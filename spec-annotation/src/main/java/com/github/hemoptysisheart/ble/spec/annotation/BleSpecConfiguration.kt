package com.github.hemoptysisheart.ble.spec.annotation

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers) > [Assigned Numbers Repository (YAML)](https://bitbucket.org/bluetooth-SIG/public/src/main/assigned_numbers)
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BleSpecConfiguration(
    val deviceClass: ClassOfDeviceConfiguration = ClassOfDeviceConfiguration(),

    /**
     * "3.4 GATT Services"
     */
    val gattService: GattServiceConfiguration = GattServiceConfiguration()
)
