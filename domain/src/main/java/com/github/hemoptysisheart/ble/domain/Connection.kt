package com.github.hemoptysisheart.ble.domain

interface Connection {
    enum class State {
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
        DISCONNECTING
    }

    val device: Device

    val state: State
}
