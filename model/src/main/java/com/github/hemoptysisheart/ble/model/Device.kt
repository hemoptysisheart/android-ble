package com.github.hemoptysisheart.ble.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.content.Context
import com.github.hemoptysisheart.ble.domain.AbstractDevice
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import com.github.hemoptysisheart.ble.spec.core.MajorServiceClass

@SuppressLint("MissingPermission")
class Device(
    private val context: Context,
    private val _rssi: Int,
    internal val source: BluetoothDevice
) : AbstractDevice() {
    private val tag = source.address.substring(11)

    override val name = source.name

    override val address = source.address

    override val category: DeviceClass = DeviceClass(source.bluetoothClass)

    override val services: List<MajorServiceClass> = MajorServiceClass(source.bluetoothClass)

    override val rssi: Int = _rssi

    init {
        require(BluetoothDevice.DEVICE_TYPE_CLASSIC != source.type) {
            "classic device not supported : type=${source.type}"
        }
    }

    override fun connect(): Connection {
        connection = Connection(tag) { callback ->
            source.connectGatt(context, false, callback, TRANSPORT_LE)
        }
        return connection!!
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }

    override fun equals(other: Any?) = this === other || (
            other is Device &&
                    source == other.source
            )

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + source.hashCode()
        return result
    }

    override fun toString() = listOf(
        "name=$name",
        "address=$address",
        "category=$category",
        "services=$services",
        "rssi=$rssi",
        "connection=$connection"
    ).joinToString(", ", "Device/$tag(", ")")
}
