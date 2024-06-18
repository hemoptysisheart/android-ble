package com.github.hemoptysisheart.ble.model

import android.util.Log
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.defaultToString

class ConnectionModelImpl(
    private val deviceCacheModel: DeviceCacheModel
) : ConnectionModel {
    companion object {
        private const val TAG = "ConnectionModelImpl"
    }

    override fun connect(address: String): Connection {
        Log.d(TAG, "#connect args : address=$address")

        val device = deviceCacheModel[address]
            ?: throw IllegalArgumentException("device not found : address=$address")
        Log.d(TAG, "#connect : device=$device")

        val connection = Connection(device as Device)

        Log.d(TAG, "#connect return : connection=$connection")
        return connection
    }

    override fun toString() = listOf(
        "deviceCacheModel=${deviceCacheModel.defaultToString()}"
    ).joinToString(", ", "$TAG(", ")")
}
