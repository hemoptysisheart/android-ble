package com.github.hemoptysisheart.ble.spec.processor.processor

import com.github.hemoptysisheart.ble.spec.processor.LOGGER
import com.github.hemoptysisheart.ble.spec.processor.config.Config
import com.github.hemoptysisheart.ble.spec.processor.data.GattStandardAttributeList
import com.github.hemoptysisheart.ble.spec.processor.loader.GattServiceLoader
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

class GattServiceProcessor(
    private val configuration: Config
) {
    companion object {
        private const val TAG = "GattServiceProcessor"

        const val FILE_NAME = "GattService.kt"
        const val MAP_NAME = "GATT_SERVICES"
        const val MAP_TYPE_NAME = "Service"
        const val MAP_ACTUAL_NAME = "StandardService"
    }

    fun process(configuration: KSAnnotation) {
        LOGGER.info("$TAG#process args : configuration=$configuration")

        val file = Paths.get(
            this.configuration.source.path.absolutePath,
            configuration.arguments.first { "file" == it.name?.asString() }.value as String
        ).toFile()
        val loader = GattServiceLoader(file)
        LOGGER.info("$TAG#process : file=$file, loader=$loader")

        val data = loader.load()
        generate(data)
    }

    fun generate(data: GattStandardAttributeList) {
        val packageName = "${configuration.target.packageName}.core"
        val mapBuilder = CodeBlock.builder()
            .add("listOf(\n")
        for (service in data.uuids) {
            mapBuilder.add(
                "$MAP_ACTUAL_NAME(%L, %S, %S),\n",
                "0x${service.uuid.toString(16).uppercase()}",
                service.id,
                service.name
            )
        }
        mapBuilder.add(").associateBy { it.uuid }")

        val builder = PropertySpec.builder(
            name = MAP_NAME,
            type = Map::class.asClassName()
                .parameterizedBy(
                    ClassName(UUID::class.java.`package`.name, UUID::class.simpleName!!),
                    ClassName(packageName, MAP_TYPE_NAME)
                ),
        ).initializer(mapBuilder.build())

        FileSpec.builder(packageName, FILE_NAME)
            .addProperty(builder.build())
            .build()
            .writeTo(configuration.target.codeGenerator, Dependencies(true))
    }
}
