package com.github.hemoptysisheart.ble.spec

import android.bluetooth.BluetoothClass

fun MajorDeviceClass(bleClass: BluetoothClass) = MajorDeviceClass.entries.firstOrNull { deviceClass ->
    bleClass.majorDeviceClass == (deviceClass.value shl 8)
} ?: MajorDeviceClass.UNCATEGORIZED
