package com.github.hemoptysisheart.ble.spec.core

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothClass.Device

/**
 * Android 플랫폼의 [BluetoothClass]를 [MajorServiceClass] 목록으로 변환한다.
 */
fun MajorServiceClass(bleClass: BluetoothClass): List<MajorServiceClass> = MajorServiceClass.entries.filter {
    bleClass.hasService(1 shl it.mask)
}

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

/**
 *
 * Android 플랫폼의 [BluetoothClass]를 [DeviceClass]로 변환한다.
 */
fun DeviceClass(bleClass: BluetoothClass): DeviceClass {
    val major = MajorDeviceClass(bleClass)
    val value = (bleClass.deviceClass and 0xFC)
    val deviceClass = DeviceClass.entries.firstOrNull {
        it.major == major &&
                it.value == value
    }
    println("bleClass=$bleClass(0x${bleClass.majorDeviceClass.toString(16)}, 0x${bleClass.deviceClass.toString(16)}), major=$major, value=$value => $deviceClass")
    return deviceClass
        ?: throw IllegalArgumentException("unsupported deviceClass : bleClass=$bleClass")
}