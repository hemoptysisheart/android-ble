package com.github.hemoptysisheart.ble.spec.core

/**
 * BLE GATT 서비스.
 *
 * 참고 :
 * - Bluetooth Core Specification [Vol 3] Part C, Section 12
 * - Bluetooth Core Specification [Vol 3] Part F, Section 2
 * - [`characteristic_uuids.yaml`](https://bitbucket.org/bluetooth-SIG/public/src/main/assigned_numbers/uuids/characteristic_uuids.yaml)
 */
interface Characteristic : Attribute {
    val id: String?

    val name: String?
}
