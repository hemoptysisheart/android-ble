package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile.STATE_CONNECTED
import android.bluetooth.BluetoothProfile.STATE_CONNECTING
import android.bluetooth.BluetoothProfile.STATE_DISCONNECTED
import android.bluetooth.BluetoothProfile.STATE_DISCONNECTING
import android.util.Log
import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.AbstractConnection
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.spec.core.CustomCharacteristic
import com.github.hemoptysisheart.ble.spec.core.CustomService
import com.github.hemoptysisheart.ble.spec.core.Service
import kotlinx.coroutines.flow.update

class Connection(
    device: Device,
    private val gattConnector: (BluetoothGattCallback) -> BluetoothGatt
) : AbstractConnection<Device>("Connection/${device.address}", device) {
    private val callback = object : BluetoothGattCallback() {
        @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            Log.d(tag, "#callback.onConnectionStateChange args : gatt=$gatt, status=$status, newState=$newState")
            require(null != gatt) { "gatt is required" }

            if (this@Connection.gatt !== gatt) {
                Log.e(
                    tag,
                    "#callback.onConnectionStateChange gatt does not match : gatt=$gatt, connection.gatt=${this@Connection.gatt}"
                )
            }

            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=$status")
            }

            val newLevel = when (newState) {
                STATE_DISCONNECTED ->
                    Level.DISCONNECTED

                STATE_CONNECTING ->
                    Level.CONNECTING

                STATE_CONNECTED ->
                    Level.CONNECTED

                STATE_DISCONNECTING ->
                    Level.DISCONNECTING

                else ->
                    throw IllegalArgumentException("unsupported newState : newState=$newState")
            }
            Log.i(tag, "#callback.onConnectionStateChange : newLevel=$newLevel")

            level = newLevel
            if (Level.CONNECTED == newLevel) {
                gatt!!.requestMtu(Connection.MTU_DEFAULT)
                gatt.discoverServices()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            Log.d(tag, "#callback.onServicesDiscovered args : gatt=$gatt, status=$status")
            require(null != gatt) { "gatt is required" }

            if (this@Connection.gatt !== gatt) {
                Log.e(
                    tag,
                    listOf(
                        "gatt=$gatt",
                        "connection.gatt=${this@Connection.gatt}"
                    ).joinToString(", ", "#callback.onServicesDiscovered gatt does not match : ")
                )
            }

            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=$status")
            }

            updateService()
        }

        override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) {
            Log.d(tag, "#callback.onMtuChanged args : gatt=$gatt, mtu=$mtu, status=$status")
            require(null != gatt) { "gatt is required" }

            if (BluetoothGatt.GATT_SUCCESS == status) {
                this@Connection.mtu = mtu
            }
        }
    }

    private lateinit var gatt: BluetoothGatt

    override var mtu: Int? = null
        private set(value) {
            Log.d(tag, "#mtu.set : $value")

            field = value
            _state.update { it.copy(mtu = value) }
        }

    private fun updateService() {
        Log.d(tag, "#updateService called.")

        if (Level.CONNECTED != level) {
            throw IllegalStateException("connection is not connected : level=$level")
        }

        services = gatt.services.map { service ->
            Log.d(tag, "#updateService service=$service")
            Service(
                type = Service(service.uuid) ?: CustomService(service.uuid),
                characteristics = service.characteristics.map { characteristic ->
                    Log.d(tag, "#updateService characteristic=$characteristic")
                    Characteristic(
                        type = com.github.hemoptysisheart.ble.spec.core.Characteristic(characteristic.uuid)
                            ?: CustomCharacteristic(characteristic.uuid)
                    )
                }
            )
        }
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override fun connect() {
        Log.d(tag, "#connect called.")

        if (Level.DISCONNECTED != level) {
            throw IllegalStateException("connection is not disconnected : level=$level")
        }

        level = Level.CONNECTING
        gatt = gattConnector(callback)
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override fun disconnect() {
        Log.d(tag, "#disconnect called.")

        if (Level.CONNECTED != level) {
            throw IllegalStateException("connection is not connected : level=$level")
        }

        level = Level.DISCONNECTING
        services = null
        gatt.disconnect()
    }

    override fun toString() = listOf(
        "device=$device",
        "state=${state.value}",
        "gatt=${
            if (::gatt.isInitialized) {
                gatt.toString()
            } else {
                "not initialized"
            }
        }",
        "callback=$callback"
    ).joinToString(", ", "$tag(", ")")
}
