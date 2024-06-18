package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothDevice
import com.github.hemoptysisheart.ble.domain.AbstractConnection
import com.github.hemoptysisheart.ble.domain.Connection.State
import kotlinx.coroutines.withContext

class Connection(
    private val source: BluetoothDevice
) : AbstractConnection() {
    override suspend fun connect() {
        if (state != State.DISCONNECTED) {
            throw IllegalStateException("Connection is not disconnected : state=$state")
        }

        withContext(dispatcher) {
            state = State.CONNECTING
        }
    }

    override suspend fun disconnect() {
        TODO("Not yet implemented")
    }
}