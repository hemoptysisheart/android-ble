package com.github.hemoptysisheart.ble.spec.processor.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers) > "2.8.2 Major Device Class"
 */
@Serializable
data class DeviceClass(
    @SerialName("major")
    val major: Int,
    @SerialName("name")
    val name: String,
    @SerialName("subsplit")
    val subSplit: Int? = null,
    @SerialName("minor")
    val minor: List<DeviceMinorClass> = emptyList(),
    @SerialName("minor_bits")
    val minorBits: List<MinorBit>? = null,
    @SerialName("subminor")
    val subMinor: List<DeviceMinorClass>? = null
)
