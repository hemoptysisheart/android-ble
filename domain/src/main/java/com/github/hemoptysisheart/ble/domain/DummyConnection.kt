package com.github.hemoptysisheart.ble.domain

class DummyConnection(override val state: Connection.State = Connection.State.DISCONNECTED) : Connection {
    override suspend fun connect() {
    }

    override suspend fun disconnect() {
    }
}
