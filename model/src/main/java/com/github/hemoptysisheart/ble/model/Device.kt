package com.github.hemoptysisheart.ble.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.DEVICE_TYPE_CLASSIC
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.content.Context
import android.util.Log
import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.domain.Device.State
import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import com.github.hemoptysisheart.ble.spec.core.MajorServiceClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
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

    override val serviceClasses: List<MajorServiceClass> = MajorServiceClass(source.bluetoothClass)

    override var rssi: Int = _rssi
        private set(value) {
            field = value
            _state.update { it.copy(rssi = value) }
        }

    override var connection: Connection? = null
        private set(value) {
            field = value
            _state.update {
                it.copy(
                    connection = value?.let { connection ->
                        com.github.hemoptysisheart.ble.domain.Connection.State(
                            connection.level,
                            connection.mtu,
                            connection.services
                        )
                    }
                )
            }
        }

    private val _state = MutableStateFlow(State(name, address, category, serviceClasses, rssi, null))
    override val state: StateFlow<State> = _state

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

            _state.update {
                it.copy(
                    connection = com.github.hemoptysisheart.ble.domain.Connection.State(
                        level = connection.level,
                        mtu = connection.mtu,
                        services = connection.services
                    )
                )
            }
        }
        this.connection = connection

        Log.d(tag, "#connect return : $connection")
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
        "services=$serviceClasses",
        "rssi=$rssi",
        "connection=$connection"
    ).joinToString(", ", "Device/$tag(", ")")
}
