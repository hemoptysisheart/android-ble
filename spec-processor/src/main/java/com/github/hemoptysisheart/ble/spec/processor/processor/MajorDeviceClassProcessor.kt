package com.github.hemoptysisheart.ble.spec.processor.processor

import com.github.hemoptysisheart.ble.spec.processor.LOGGER
import com.github.hemoptysisheart.ble.spec.processor.config.Config
import com.github.hemoptysisheart.ble.spec.processor.data.DeviceClass
import com.google.devtools.ksp.processing.Dependencies
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

class MajorDeviceClassProcessor(
    private val configuration: Config
) {
    companion object {
        private const val TAG = "MajorDeviceClassProcessor"

        const val CLASS_NAME = "MajorDeviceClass"
        const val PROP_VALUE = "value"
        const val PROP_LABEL = "label"
    }

    fun process(deviceClasses: List<DeviceClass>) {
        LOGGER.info("$TAG#process args : deviceClasses=$deviceClasses")

        val builder = TypeSpec.enumBuilder(CLASS_NAME)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(PROP_VALUE, Int::class)
                    .addParameter(PROP_LABEL, String::class)
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

        for (clz in deviceClasses) {
            builder.addEnumConstant(
                name = name2enumName(clz.name),
                typeSpec = TypeSpec.anonymousClassBuilder()
                    .addSuperclassConstructorParameter("%L", clz.major)
                    .addSuperclassConstructorParameter("%S", clz.name.trim())
                    .build()
            )
        }
        val spec = builder.build()
        LOGGER.info("$TAG#process : spec=$spec")

        FileSpec.builder("${configuration.target.packageName}.core", CLASS_NAME)
            .addType(spec)
            .build()
            .writeTo(configuration.target.codeGenerator, Dependencies(true))
    }

    @Suppress("MemberVisibilityCanBePrivate")
    internal fun name2enumName(source: String): String {
        val endIndex = source.indexOf(" ").takeIf { it != -1 }
            ?: source.length
        return source.substring(0, endIndex)
            .replace(Regex("\\W"), "_")
            .uppercase()
    }
}
