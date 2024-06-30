package com.github.hemoptysisheart.ble.spec.annotation

annotation class GattCharacteristicConfiguration(
    /**
     * [`characteristic_uuids.yaml`](https://bitbucket.org/bluetooth-SIG/public/src/main/assigned_numbers/uuids/characteristic_uuids.yaml)의 경로.
     */
    val file: String = "assigned_numbers/uuids/characteristic_uuids.yaml"
)
