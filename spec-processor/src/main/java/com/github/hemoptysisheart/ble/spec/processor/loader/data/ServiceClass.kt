package com.github.hemoptysisheart.ble.spec.processor.loader.data

import kotlinx.serialization.Serializable

@Serializable
data class ServiceClass(
    val bit: Int,
    val name: String
)
