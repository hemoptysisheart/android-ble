package com.github.hemoptysisheart.ble.spec.processor.loader.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ClassOfDevice(
    @SerialName("cod_services")
    val serviceClass: List<ServiceClass>,
    @SerialName("cod_device_class")
    val deviceClass: List<DeviceClass>
)