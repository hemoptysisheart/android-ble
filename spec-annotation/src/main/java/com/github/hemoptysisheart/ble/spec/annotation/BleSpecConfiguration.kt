package com.github.hemoptysisheart.ble.spec.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class BleSpecConfiguration(
    val deviceClass: ClassOfDeviceConfiguration = ClassOfDeviceConfiguration()
)
