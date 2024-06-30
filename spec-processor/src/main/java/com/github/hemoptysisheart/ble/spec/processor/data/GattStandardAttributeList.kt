package com.github.hemoptysisheart.ble.spec.processor.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * - Bluetooth Core Specification [Vol 3] Part G, Section 3.3.1
 * - [`service_uuids.yaml`](https://bitbucket.org/bluetooth-SIG/public/src/main/assigned_numbers/uuids/service_uuids.yaml)
 */
@Serializable
data class GattStandardAttributeList(
    @SerialName("uuids")
    val uuids: List<GattStandardAttribute>
)
