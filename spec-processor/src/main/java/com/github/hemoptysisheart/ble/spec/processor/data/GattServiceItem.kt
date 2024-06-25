package com.github.hemoptysisheart.ble.spec.processor.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GattServiceItem(
    @SerialName("uuid")
    val uuid: Int,
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: String
)
