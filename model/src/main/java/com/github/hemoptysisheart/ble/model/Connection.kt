package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.util.Log
import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.AbstractConnection
import com.github.hemoptysisheart.ble.domain.Connection.Companion.MTU_DEFAULT
import com.github.hemoptysisheart.ble.domain.Connection.Level
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Connection(
    device: Device,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val builder: (BluetoothGattCallback) -> BluetoothGatt
) : AbstractConnection<Device>("Connection/${device.address}", device) {
    private val gattWrapper = GattWrapper(tag, builder)

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override fun connect() {
        Log.d(tag, "#connect called.")

        if (Level.DISCONNECTED != level) {
            throw IllegalStateException("connection is not disconnected : level=$level")
        }

        scope.launch {
            level = Level.CONNECTING
            level = gattWrapper.connect()
            mtu = gattWrapper.setMtu(MTU_DEFAULT)
            services = gattWrapper.services()
        }
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override fun disconnect() {
        Log.d(tag, "#disconnect called.")

        if (Level.CONNECTED != level) {
            throw IllegalStateException("connection is not connected : level=$level")
        }

        scope.launch {
            level = Level.DISCONNECTING
            services = null
            mtu = null
            level = gattWrapper.disconnect()
        }
    }

    override fun toString() = listOf(
        "device=$device",
        "state=${state.value}",
        "gatt=$gattWrapper",
    ).joinToString(", ", "$tag(", ")")
}
