package com.github.hemoptysisheart.ble.spec.processor.processor

import com.github.hemoptysisheart.ble.spec.processor.LOGGER
import com.github.hemoptysisheart.ble.spec.processor.config.Config
import com.github.hemoptysisheart.ble.spec.processor.data.DeviceClass
import com.google.devtools.ksp.processing.Dependencies
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers) >
 * "2.8.2.1 Minor Device Class field – Computer Major Class" ~ "2.8.2.9 Minor Device Class field – Health Major Class"
 */
class DeviceClassProcessor(
    private val configuration: Config
) {
    companion object {
        private const val TAG = "DeviceClassProcessor"

        const val CLASS_NAME = "DeviceClass"
        const val PROP_MAJOR = "major"
        const val PROP_VALUE = "value"
        const val PROP_LABEL = "label"

        internal fun name2enumName(source: String): String {
            val endIndex = source.indexOf("(").takeIf { it != -1 }
                ?: source.length
            return source.substring(0, endIndex)
                .trim()
                .replace(Regex("\\W"), "_")
                .uppercase()
        }
    }

    fun process(deviceClasses: List<DeviceClass>) {
        LOGGER.info("$TAG#process args : deviceClasses=$deviceClasses")

        val majorType = ClassName(
            packageName = "${configuration.target.packageName}.core",
            MajorDeviceClassProcessor.CLASS_NAME
        )
        val builder = TypeSpec.enumBuilder(CLASS_NAME)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(
                        PROP_MAJOR,
                        type = majorType
                    )
                    .addParameter(PROP_VALUE, Int::class)
                    .addParameter(PROP_LABEL, String::class)
                    .build()
            )
            .addProperty(
                PropertySpec.builder(PROP_MAJOR, majorType)
                    .initializer(PROP_MAJOR)
                    .build()
            )
            .addProperty(
                PropertySpec.builder(PROP_VALUE, Int::class)
                    .initializer(PROP_VALUE)
                    .build()
            )
            .addProperty(
                PropertySpec.builder(PROP_LABEL, String::class)
                    .initializer(PROP_LABEL)
                    .build()
            )

        for (majorClass in deviceClasses) {
            processMajor(builder, majorClass)
        }

        FileSpec.builder("${configuration.target.packageName}.core", CLASS_NAME)
            .addType(builder.build())
            .build()
            .writeTo(configuration.target.codeGenerator, Dependencies(true))
    }

    private fun processMajor(builder: TypeSpec.Builder, majorClass: DeviceClass) {
        val majorClassLabel = MajorDeviceClassProcessor.name2enumName(majorClass.name)
        val major = MemberName(
            enclosingClassName = ClassName(
                packageName = "${configuration.target.packageName}.core",
                MajorDeviceClassProcessor.CLASS_NAME
            ),
            simpleName = MajorDeviceClassProcessor.name2enumName(majorClass.name)
        )

        // minor 항목.
        if (majorClass.minor.isEmpty()) {
            builder.addEnumConstant(
                name = "${majorClassLabel}_$majorClassLabel",
                typeSpec = TypeSpec.anonymousClassBuilder()
                    .addKdoc(majorClass.name.replace("%", "%%"))
                    .addSuperclassConstructorParameter(CodeBlock.of("%M", major))
                    .addSuperclassConstructorParameter("%L", majorClass.major)
                    .addSuperclassConstructorParameter("%S", majorClass.name.trim())
                    .build()
            )
        } else for (minorClass in majorClass.minor) {
            val minorClassLabel = "${majorClassLabel}_${name2enumName(minorClass.name)}"
            LOGGER.info("$TAG#processMajor : major=$major, minor=$minorClassLabel")

            val value = if (null != majorClass.subSplit) {
                minorClass.value shl (8 - majorClass.subSplit)
            } else {
                minorClass.value
            }

            builder.addEnumConstant(
                name = minorClassLabel,
                typeSpec = TypeSpec.anonymousClassBuilder()
                    .addKdoc(minorClass.name.replace("%", "%%"))
                    .addSuperclassConstructorParameter(CodeBlock.of("%M", major))
                    .addSuperclassConstructorParameter("%L", value)
                    .addSuperclassConstructorParameter("%S", minorClass.name.trim())
                    .build()
            )
        }

        for (minorBit in majorClass.minorBits ?: emptyList()) {
            builder.addEnumConstant(
                name = "${majorClassLabel}_${name2enumName(minorBit.name)}",
                typeSpec = TypeSpec.anonymousClassBuilder()
                    .addKdoc(minorBit.name.replace("%", "%%"))
                    .addSuperclassConstructorParameter(CodeBlock.of("%M", major))
                    .addSuperclassConstructorParameter("%L", 1 shl minorBit.value)
                    .addSuperclassConstructorParameter("%S", minorBit.name.trim())
                    .build()
            )
        }

        for (subMinorClass in majorClass.subMinor ?: emptyList()) {
            builder.addEnumConstant(
                name = "${majorClassLabel}_${name2enumName(subMinorClass.name)}",
                typeSpec = TypeSpec.anonymousClassBuilder()
                    .addKdoc(subMinorClass.name.replace("%", "%%"))
                    .addSuperclassConstructorParameter(CodeBlock.of("%M", major))
                    .addSuperclassConstructorParameter("%L", subMinorClass.value shl majorClass.subSplit!!)
                    .addSuperclassConstructorParameter("%S", subMinorClass.name.trim())
                    .build()
            )
        }
    }
}