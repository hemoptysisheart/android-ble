package com.github.hemoptysisheart.ble.spec.processor.data

import kotlinx.serialization.Serializable

@Serializable
data class ServiceClass(
    val bit: Int,
    val name: String
)
