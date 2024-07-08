package com.github.hemoptysisheart.ble.spec.annotation

annotation class GattDescriptorConfiguration(
    /**
     * [`assigned_numbers/uuids/descriptors.yaml`](https://bitbucket.org/bluetooth-SIG/public/src/main/assigned_numbers/uuids/descriptors.yaml)
     */
    val file: String = "assigned_numbers/uuids/descriptors.yaml"
)
