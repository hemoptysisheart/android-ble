package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.defaultToString

class ConnectionModelImpl(
    private val context: Context,
    private val deviceCacheModel: DeviceCacheModel
) : ConnectionModel {
    companion object {
        private const val TAG = "ConnectionModelImpl"
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override fun connect(address: String): Connection {
        Log.d(TAG, "#connect args : address=$address")

        val device = deviceCacheModel[address]
            ?: throw IllegalArgumentException("device not found : address=$address")
        Log.d(TAG, "#connect : device=$device")
        require(device is Device) { "unsupported device type : device::class=${device::class}" }


        val connection = Connection(
            device = device,
            gatt = { callback -> device.source.connectGatt(context, false, callback, TRANSPORT_LE) }
        )

        Log.d(TAG, "#connect return : connection=$connection")
        return connection
    }

    override fun toString() = listOf(
        "deviceCacheModel=${deviceCacheModel.defaultToString()}"
    ).joinToString(", ", "$TAG(", ")")
}
