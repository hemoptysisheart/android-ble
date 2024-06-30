package com.github.hemoptysisheart.ble.spec.processor.processor

import com.github.hemoptysisheart.ble.spec.processor.LOGGER
import com.github.hemoptysisheart.ble.spec.processor.config.Config
import com.github.hemoptysisheart.ble.spec.processor.loader.GattCharacteristicLoader
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSAnnotation
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.writeTo
import java.nio.file.Paths
import java.util.UUID

class GattCharacteristicProcessor(
    private val configuration: Config
) {
    companion object {
        private const val TAG = "GattCharacteristicProcessor"

        const val FILE_NAME = "GattCharacteristic.kt"
        const val MAP_NAME = "GATT_CHARACTERISTICS"
        const val MAP_TYPE_NAME = "Characteristic"
        const val MAP_ACTUAL_NAME = "StandardCharacteristic"
    }

    fun process(configuration: KSAnnotation) {
        LOGGER.info("$TAG#process args : configuration=$configuration")

        val file = Paths.get(
            this.configuration.source.path.absolutePath,
            configuration.arguments.first { "file" == it.name?.asString() }.value as String
        ).toFile()
        val loader = GattCharacteristicLoader(file)
        LOGGER.info("${TAG}#process : file=$file, loader=$loader")

        val packageName = "${this.configuration.target.packageName}.core"
        val mapBuilder = CodeBlock.builder()
            .add("listOf(\n")
        for (characteristic in loader.load().uuids) {
            LOGGER.info("$TAG#process : characteristic=$characteristic")
            mapBuilder.add(
                "$MAP_ACTUAL_NAME(%L, %S, %S),\n",
                "0x${characteristic.uuid.toString(16).uppercase()}",
                characteristic.id,
                characteristic.name
            )
        }
        mapBuilder.add(").associateBy { it.uuid }")

        FileSpec.builder(packageName, FILE_NAME)
            .addProperty(
                PropertySpec.builder(
                    MAP_NAME, Map::class.asClassName()
                        .parameterizedBy(
                            ClassName(UUID::class.java.`package`.name, UUID::class.simpleName!!),
                            ClassName(packageName, MAP_TYPE_NAME)
                        )
                ).initializer(mapBuilder.build()).build()
            )
            .build()
            .writeTo(this.configuration.target.codeGenerator, Dependencies(true))
    }

    override fun toString() = "$TAG(configuration=$configuration)"
}
