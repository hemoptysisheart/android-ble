package com.github.hemoptysisheart.ble.domain

interface Connection {
    enum class State {
        CONNECTING,
        CONNECTED,
        DISCONNECTING,
        DISCONNECTED
    }

    val state: State

    suspend fun connect()

    suspend fun disconnect()
}
