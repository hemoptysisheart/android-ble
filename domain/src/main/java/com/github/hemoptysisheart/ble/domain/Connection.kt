package com.github.hemoptysisheart.ble.domain

import kotlinx.coroutines.flow.StateFlow

/**
 * Bluetooth LE 연결.
 */
interface Connection {
    companion object {
        /**
         * Maximum Transmission Unit 기본값.
         */
        const val MTU_DEFAULT = 517
    }

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
        val level: Level,

        /**
         * Maximum Transmission Unit
         */
        val mtu: Int? = null,

        /**
         * 연결이 사용할 수 있는 서비스 목록.
         */
        val services: List<Service>? = null
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
     * Maximum Transmission Unit
     */
    val mtu: Int?

    /**
     * 연결이 사용할 수 있는 서비스 목록.
     * [level]이 [Level.CONNECTED]일 때만 `null`이 아니다.
     */
    val services: List<Service>?

    /**
     * 기기에 연결을 시도한다.
     */
    fun connect()

    /**
     * 기기와의 연결을 끊는다.
     */
    fun disconnect()
}
