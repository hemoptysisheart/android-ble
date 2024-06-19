package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile.STATE_CONNECTED
import android.bluetooth.BluetoothProfile.STATE_CONNECTING
import android.bluetooth.BluetoothProfile.STATE_DISCONNECTED
import android.bluetooth.BluetoothProfile.STATE_DISCONNECTING
import android.util.Log
import com.github.hemoptysisheart.ble.domain.AbstractConnection
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.ConnectionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class Connection(
    device: Device,
    gatt: (BluetoothGattCallback) -> BluetoothGatt
) : AbstractConnection<Device>(device) {
    companion object {
        internal const val TAG = "Connection"
    }

    private val callback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            Log.d(TAG, "#callback.onConnectionStateChange args : gatt=$gatt, status=$status, newState=$newState")
            require(null != gatt) { "gatt is required" }

            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=$status")
            }

            val connectionState = when (newState) {
                STATE_DISCONNECTED ->
                    ConnectionState.DISCONNECTED

                STATE_CONNECTING ->
                    ConnectionState.CONNECTING

                STATE_CONNECTED ->
                    ConnectionState.CONNECTED

                STATE_DISCONNECTING ->
                    ConnectionState.DISCONNECTING

                else ->
                    throw IllegalArgumentException("unsupported newState : newState=$newState")
            }

            _state.update { it.copy(connectionState = connectionState) }
            Log.i(TAG, "#callback.onConnectionStateChange : connection=${this@Connection}")
        }
    }

    private val gatt = gatt(callback)

    private val _state = MutableStateFlow(Connection.State(ConnectionState.CONNECTING))
    override val state: StateFlow<Connection.State> = _state

    override fun toString() = listOf(
        "device=$device",
        "state=${state.value}",
        "gatt=$gatt",
        "callback=$callback"
    ).joinToString(", ", "$TAG(", ")")
}
