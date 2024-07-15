package com.github.hemoptysisheart.ble.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.DEVICE_TYPE_CLASSIC
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.content.Context
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import com.github.hemoptysisheart.ble.spec.core.MajorServiceClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class Device(
    private val context: Context,
    private val _rssi: Int,
    internal val source: BluetoothDevice,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : Device {
    private val tag = source.address.substring(11)

    override val name = source.name

    override val address = source.address

    override val category: DeviceClass = DeviceClass(source.bluetoothClass)

    override val services: List<MajorServiceClass> = MajorServiceClass(source.bluetoothClass)

    override val rssi: Int = _rssi

    override var connection: Connection? = null
        private set

    init {
        require(DEVICE_TYPE_CLASSIC != source.type) {
            "classic device not supported : type=${source.type}"
        }
    }

    override fun connect(): Connection {
        val connection = Connection(tag, scope) { callback ->
            source.connectGatt(context, false, callback, TRANSPORT_LE)
        }

        scope.launch {
            connection.requestMtu(Connection.MTU_DEFAULT)
        }

        this.connection = connection
        return connection
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }

    override fun equals(other: Any?) = this === other || (
            other is Device &&
                    address == other.address
            )

    override fun hashCode() = address.hashCode()

    override fun toString() = listOf(
        "name=$name",
        "address=[ PROTECTED ]",
        "category=$category",
        "services=$services",
        "rssi=$rssi",
        "connection=$connection"
    ).joinToString(", ", "Device/$tag(", ")")
}
