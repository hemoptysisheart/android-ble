package com.github.hemoptysisheart.ble.spec.processor.data

import kotlinx.serialization.Serializable

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers) > "2.8.2 Major Device Class"
 */
@Serializable
data class DeviceMinorClass(
    val value: Int,
    val name: String
)
