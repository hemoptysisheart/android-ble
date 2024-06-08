package com.github.hemoptysisheart.ble.spec.processor.data

import kotlinx.serialization.Serializable

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers) > "2.8.1 Major Service Classes"
 */
@Serializable
data class ServiceClass(
    val bit: Int,
    val name: String
)
