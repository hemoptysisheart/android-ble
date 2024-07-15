package com.github.hemoptysisheart.ble.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.github.hemoptysisheart.ble.domain.AbstractDevice
import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import com.github.hemoptysisheart.ble.spec.core.MajorServiceClass

@SuppressLint("MissingPermission")
class Device(
    val source: BluetoothDevice,
    override val rssi: Int
) : AbstractDevice() {
    override val name = source.name

    override val address = source.address

    override val category: DeviceClass = DeviceClass(source.bluetoothClass)

    override val services: List<MajorServiceClass> = MajorServiceClass(source.bluetoothClass)

    init {
        require(BluetoothDevice.DEVICE_TYPE_CLASSIC != source.type) {
            "classic device not supported : type=${source.type}"
        }
    }
}
