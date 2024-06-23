package com.github.hemoptysisheart.ble.domain

import kotlinx.coroutines.flow.StateFlow

/**
 * Bluetooth LE 연결.
 */
interface Connection {
    /**
     * 연결 수준.
     */
    enum class Level(
        val label: String
    ) {
        DISCONNECTED("Disconnected"),
        CONNECTING("Connecting"),
        CONNECTED("Connected"),
        DISCONNECTING("Disconnecting")
    }

    /**
     * 연결 상태.
     */
    data class State(
        /**
         * 연결 수준.
         */
        val level: Level
    )

    /**
     * 연결 상태.
     */
    val state: StateFlow<State>

    /**
     * 연결 대상 기기.
     */
    val device: Device

    /**
     * 기기 연결 상태.
     */
    val level: Level

    /**
     * 기기에 연결을 시도한다.
     */
    fun connect()

    /**
     * 기기와의 연결을 끊는다.
     */
    fun disconnect()
}
