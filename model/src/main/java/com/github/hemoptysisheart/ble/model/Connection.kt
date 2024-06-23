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
import com.github.hemoptysisheart.ble.domain.Connection.Level

class Connection(
    device: Device,
    private val gattConnector: (BluetoothGattCallback) -> BluetoothGatt
) : AbstractConnection<Device>("Connection/${device.address}", device) {
    private val callback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            Log.d(tag, "#callback.onConnectionStateChange args : gatt=$gatt, status=$status, newState=$newState")
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
            Log.i(tag, "#callback.onConnectionStateChange : connection=${this@Connection}")
        }
    }

    private lateinit var gatt: BluetoothGatt

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
