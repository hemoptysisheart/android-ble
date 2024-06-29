package com.github.hemoptysisheart.ble.spec.processor.processor

import com.github.hemoptysisheart.ble.spec.processor.LOGGER
import com.github.hemoptysisheart.ble.spec.processor.config.Config
import com.google.devtools.ksp.symbol.KSAnnotation

class BleSpecConfigurationProcessor(
    private val configuration: Config
) {
    companion object {
        private const val TAG = "BleSpecConfigurationProcessor"
    }

    fun process(configuration: KSAnnotation) {
        LOGGER.info("$TAG#process args : configuration=${configuration}")

        val deviceClass = configuration.arguments
            .first { "deviceClass" == it.name?.asString() }
            .value as KSAnnotation
        LOGGER.info("$TAG#process : deviceClass=$deviceClass")

        ClassOfDeviceConfigurationProcessor(this.configuration)
            .process(deviceClass)

        GattServiceProcessor(this.configuration)
            .process(
                configuration.arguments
                    .first { "gattService" == it.name?.asString() }
                    .value as KSAnnotation
            )
        GattCharacteristicProcessor(this.configuration)
            .process(
                configuration.arguments
                    .first { "gattCharacteristic" == it.name?.asString() }
                    .value as KSAnnotation
            )
    }
}
