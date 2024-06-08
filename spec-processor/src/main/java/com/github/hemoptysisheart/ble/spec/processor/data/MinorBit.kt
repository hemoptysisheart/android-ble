package com.github.hemoptysisheart.ble.spec.processor.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers) > "2.8.2 Major Device Class"
 */
@Serializable
data class MinorBit(
    @SerialName("value")
    val value: Int,
    @SerialName("name")
    val name: String
)
