package com.github.hemoptysisheart.ble.spec.processor.loader.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinorBit(
    @SerialName("value")
    val value: Int,
    @SerialName("name")
    val name: String
)
