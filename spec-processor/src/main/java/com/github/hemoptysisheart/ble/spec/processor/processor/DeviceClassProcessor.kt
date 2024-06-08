package com.github.hemoptysisheart.ble.spec.processor.processor

import com.github.hemoptysisheart.ble.spec.processor.LOGGER
import com.github.hemoptysisheart.ble.spec.processor.data.DeviceClass

class DeviceClassProcessor {
    companion object {
        private const val TAG = "DeviceClassProcessor"
    }

    fun process(deviceClasses: List<DeviceClass>) {
        LOGGER.info("$TAG#process args : deviceClasses=$deviceClasses")
        for (device in deviceClasses) {
            process(device)
        }
    }

    fun process(deviceClass: DeviceClass) {
        LOGGER.info("$TAG#process args : deviceClass=$deviceClass")
    }
}
