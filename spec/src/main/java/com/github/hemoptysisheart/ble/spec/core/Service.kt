package com.github.hemoptysisheart.ble.spec.core

/**
 * BLE GATT 서비스.
 *
 * 참고 :
 * - Bluetooth Core Specification [Vol 3] Part G, Section 3.3.1
 * - [`service_uuids.yaml`](https://bitbucket.org/bluetooth-SIG/public/src/main/assigned_numbers/uuids/service_uuids.yaml)
 */
interface Service : Attribute {
    val characteristics: List<Characteristic>
}
