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

            state = when (newState) {
                STATE_DISCONNECTED ->
                    Connection.State.DISCONNECTED

                STATE_CONNECTING ->
                    Connection.State.CONNECTING

                STATE_CONNECTED ->
                    Connection.State.CONNECTED

                STATE_DISCONNECTING ->
                    Connection.State.DISCONNECTING

                else ->
                    throw IllegalArgumentException("unsupported newState : newState=$newState")
            }

            Log.i(TAG, "#callback.onConnectionStateChange : connection=${this@Connection}")
        }
    }

    private val gatt = gatt(callback)

    override var state: Connection.State = Connection.State.CONNECTING
        private set

    override fun toString() = listOf(
        "device=$device",
        "state=$state",
        "gatt=$gatt",
        "callback=$callback"
    ).joinToString(", ", "$TAG(", ")")
}
