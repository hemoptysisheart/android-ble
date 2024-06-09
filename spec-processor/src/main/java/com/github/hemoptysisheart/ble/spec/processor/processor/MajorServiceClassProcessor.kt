package com.github.hemoptysisheart.ble.spec.processor.processor

import com.github.hemoptysisheart.ble.spec.processor.LOGGER
import com.github.hemoptysisheart.ble.spec.processor.config.Config
import com.github.hemoptysisheart.ble.spec.processor.data.ServiceClass
import com.google.devtools.ksp.processing.Dependencies
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers) > "2.8.1 Major Service Classes"
 */
class MajorServiceClassProcessor(
    private val configuration: Config
) {
    companion object {
        private const val TAG = "MajorServiceClassProcessor"

        const val CLASS_NAME = "MajorServiceClass"
        const val PROP_MASK = "mask"
        const val PROP_LABEL = "label"
    }

    fun process(serviceClasses: List<ServiceClass>) {
        LOGGER.info("$TAG#process args : serviceClasses=$serviceClasses")

        val builder = TypeSpec.enumBuilder(CLASS_NAME)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(PROP_MASK, Int::class)
                    .addParameter(PROP_LABEL, String::class)
                    .build()
            )
            .addProperty(
                PropertySpec.builder(PROP_MASK, Int::class)
                    .initializer(PROP_MASK)
                    .build()
            )
            .addProperty(
                PropertySpec.builder(PROP_LABEL, String::class)
                    .initializer(PROP_LABEL)
                    .build()
            )

        for (clz in serviceClasses) {
            builder.addEnumConstant(
                name = name2enumName(clz.name),
                typeSpec = TypeSpec.anonymousClassBuilder()
                    .addSuperclassConstructorParameter("%L", clz.bit)
                    .addSuperclassConstructorParameter("%S", clz.name.trim())
                    .build()
            )
        }

        FileSpec.builder("${configuration.target.packageName}.core", CLASS_NAME)
            .addType(builder.build())
            .build()
            .writeTo(configuration.target.codeGenerator, Dependencies(true))
    }


    @Suppress("MemberVisibilityCanBePrivate")
    internal fun name2enumName(source: String): String {
        val endIndex = source.indexOf("(").takeIf { it != -1 }
            ?: source.length
        return source.substring(0, endIndex)
            .trim()
            .replace(Regex("\\W"), "_")
            .uppercase()
    }
}
