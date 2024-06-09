package com.github.hemoptysisheart.ble.spec.core

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothClass.Device

/**
 * Android 플랫폼의 [BluetoothClass]를 [MajorDeviceClass]로 변환한다.
 */
fun MajorDeviceClass(bleClass: BluetoothClass) = when (bleClass.majorDeviceClass) {
    Device.Major.MISC ->
        MajorDeviceClass.MISCELLANEOUS

    Device.Major.COMPUTER ->
        MajorDeviceClass.COMPUTER

    Device.Major.PHONE ->
        MajorDeviceClass.PHONE

    Device.Major.NETWORKING ->
        MajorDeviceClass.LAN_NETWORK

    Device.Major.AUDIO_VIDEO ->
        MajorDeviceClass.AUDIO_VIDEO

    Device.Major.PERIPHERAL ->
        MajorDeviceClass.PERIPHERAL

    Device.Major.IMAGING ->
        MajorDeviceClass.IMAGING

    Device.Major.WEARABLE ->
        MajorDeviceClass.WEARABLE

    Device.Major.TOY ->
        MajorDeviceClass.TOY

    Device.Major.HEALTH ->
        MajorDeviceClass.HEALTH

    Device.Major.UNCATEGORIZED ->
        MajorDeviceClass.UNCATEGORIZED

    else ->
        throw IllegalArgumentException(
            "unsupported majorDeviceClass : bleClass=$bleClass(bleClass.majorDeviceClass=${bleClass.majorDeviceClass})"
        )
}
