package com.github.hemoptysisheart.ble.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.github.hemoptysisheart.ble.spec.core.DeviceClass

@SuppressLint("MissingPermission")
class Device(
    val source: BluetoothDevice
) : com.github.hemoptysisheart.ble.domain.Device {
    override val name = source.name

    override val address = source.address

    override val category: DeviceClass = DeviceClass(source.bluetoothClass)

    override fun toString() = listOf(
        "source=$source",
        "name=$name",
        "address=$address",
    ).joinToString(", ", "Device(", ")")
}
