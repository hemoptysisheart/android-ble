package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile.STATE_CONNECTED
import android.bluetooth.BluetoothProfile.STATE_CONNECTING
import android.bluetooth.BluetoothProfile.STATE_DISCONNECTED
import android.bluetooth.BluetoothProfile.STATE_DISCONNECTING
import android.util.Log
import com.github.hemoptysisheart.ble.domain.AbstractConnection
import com.github.hemoptysisheart.ble.domain.Connection.Level
import kotlinx.coroutines.flow.update

class Connection(
    device: Device,
    private val connector: (BluetoothGattCallback) -> BluetoothGatt
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

            level = newLevel
            _state.update { it.copy(level = newLevel) }
            Log.i(TAG, "#callback.onConnectionStateChange : connection=${this@Connection}")
        }
    }

    private lateinit var gatt: BluetoothGatt

    override fun connect() {
        gatt = connector(callback)
    }

    override fun toString() = listOf(
        "device=$device",
        "state=${state.value}",
        "gatt=$gatt",
        "callback=$callback"
    ).joinToString(", ", "$TAG(", ")")
}
