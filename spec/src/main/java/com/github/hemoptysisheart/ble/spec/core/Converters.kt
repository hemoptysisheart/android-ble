package com.github.hemoptysisheart.ble.spec.core

import android.bluetooth.BluetoothClass

/**
 * Android 플랫폼의 [BluetoothClass]를 [MajorDeviceClass]로 변환한다.
 */
fun MajorDeviceClass(bleClass: BluetoothClass) = MajorDeviceClass.entries.firstOrNull { deviceClass ->
    bleClass.majorDeviceClass == (deviceClass.value shl 8)
} ?: MajorDeviceClass.UNCATEGORIZED
