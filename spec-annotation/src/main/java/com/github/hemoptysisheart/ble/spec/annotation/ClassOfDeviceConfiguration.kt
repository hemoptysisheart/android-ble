package com.github.hemoptysisheart.ble.spec.annotation

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers) > "2.8.2 Major Device Class"
 */
annotation class ClassOfDeviceConfiguration(
    /**
     * `true`면 클래스 생성.
     */
    val value: Boolean = true,

    val file: String = "assigned_numbers/core/class_of_device.yaml",
)
