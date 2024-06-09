package com.github.hemoptysisheart.ble.spec.processor.processor

import com.github.hemoptysisheart.ble.spec.annotation.ClassOfDeviceConfiguration
import com.github.hemoptysisheart.ble.spec.processor.LOGGER
import com.github.hemoptysisheart.ble.spec.processor.config.Config
import com.github.hemoptysisheart.ble.spec.processor.loader.DeviceClassLoader
import com.google.devtools.ksp.symbol.KSAnnotation
import java.nio.file.Paths

class ClassOfDeviceConfigurationProcessor(
    private val configuration: Config
) {
    companion object {
        private const val TAG = "ClassOfDeviceConfigurationProcessor"
    }

    fun process(configuration: KSAnnotation) {
        LOGGER.info("$TAG#process args : configuration=${configuration}")
        if (ClassOfDeviceConfiguration::class.simpleName != configuration.shortName.asString()) {
            throw IllegalArgumentException("unsupported annotation : configuration=$configuration")
        }

        val generate = configuration.arguments
            .first { "value" == it.name?.asString() }
            .value as Boolean
        if (!generate) {
            LOGGER.info("$TAG#process : skip")
            return
        }
        LOGGER.info("$TAG#process generate device classes.")

        val file = Paths.get(
            this.configuration.source.path.absolutePath,
            configuration.arguments.first { "file" == it.name?.asString() }.value as String
        ).toFile()
        val loader = DeviceClassLoader(file)
        val definitions = loader.load()
        LOGGER.info("$TAG#process : definitions=$definitions")

        MajorDeviceClassProcessor(this.configuration)
            .process(definitions.deviceClass)
    }
}
