package com.github.hemoptysisheart.ble.spec.processor.loader.data

import kotlinx.serialization.Serializable

@Serializable
data class DeviceMinorClass(
    val value: Int,
    val name: String
)
