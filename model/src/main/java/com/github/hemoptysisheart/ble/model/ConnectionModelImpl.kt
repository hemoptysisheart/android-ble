package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.defaultToString

class ConnectionModelImpl(
    private val context: Context,
    private val permissionModel: PermissionModel,
    private val deviceCacheModel: DeviceCacheModel
) : ConnectionModel {
    companion object {
        private const val TAG = "ConnectionModelImpl"
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override fun build(address: String): com.github.hemoptysisheart.ble.domain.Connection {
        Log.d(TAG, "#build args : address=$address")

        val device = deviceCacheModel[address]
            ?: throw IllegalArgumentException("device not found : address=$address")
        Log.d(TAG, "#build : device=$device")
        require(device is Device) { "unsupported device type : device::class=${device::class}" }

        val connection = Connection(device) { callback ->
            device.source.connectGatt(context, false, callback, TRANSPORT_LE)
        }

        Log.d(TAG, "#build return : connection=$connection")
        return connection
    }

    override fun toString() = listOf(
        "context=$context",
        "permissionModel=${permissionModel.defaultToString()}",
        "deviceCacheModel=${deviceCacheModel.defaultToString()}"
    ).joinToString(", ", "$TAG(", ")")
}
