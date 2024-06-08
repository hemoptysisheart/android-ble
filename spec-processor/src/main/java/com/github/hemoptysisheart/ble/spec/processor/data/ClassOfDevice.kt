package com.github.hemoptysisheart.ble.spec.processor.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers) >  "2.8 Class of Device"
 */
@Serializable
data class ClassOfDevice(
    @SerialName("cod_services")
    val serviceClass: List<ServiceClass>,
    @SerialName("cod_device_class")
    val deviceClass: List<DeviceClass>
)