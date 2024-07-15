package com.github.hemoptysisheart.ble.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.DEVICE_TYPE_CLASSIC
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.content.Context
import android.util.Log
import com.github.hemoptysisheart.ble.domain.Connection.Level
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
) : com.github.hemoptysisheart.ble.domain.Device {
    private val tag = "Device/${source.address.takeLast(5)}"

    override val name = source.name

    override val address = source.address

    override val category: DeviceClass = DeviceClass(source.bluetoothClass)

    override val services: List<MajorServiceClass> = MajorServiceClass(source.bluetoothClass)

    override val rssi: Int
        get() = _rssi

    override var connection: Connection? = null
        private set

    init {
        require(DEVICE_TYPE_CLASSIC != source.type) {
            "classic device not supported : type=${source.type}"
        }
    }

    override fun connect(): Connection {
        Log.d(tag, "#connect called.")
        if (null != connection) {
            throw IllegalStateException("already connected : connection=$connection")
        }

        val id = source.address.takeLast(5)
        val gatt = GattWrapper(id, scope) { callback ->
            source.connectGatt(context, false, callback, TRANSPORT_LE)
        }
        val connection = Connection(id, gatt)
        scope.launch {
            gatt.awaitConnected()
            connection.requestMtu()
            connection.services()
        }
        this.connection = connection

        return connection
    }

    override fun disconnect() {
        Log.d(tag, "#disconnect called.")

        val connection = connection
        if (null == connection) {
            throw IllegalStateException("not connected : connection=$connection")
        } else if (Level.CONNECTED != connection.level) {
            throw IllegalStateException("not connected : connection=$connection")
        }

        connection.disconnect()
        this.connection = null
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
