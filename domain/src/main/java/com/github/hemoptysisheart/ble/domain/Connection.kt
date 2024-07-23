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
        val services: List<Service> = emptyList()
    )

    /**
     * 연결 상태.
     */
    val state: StateFlow<State>

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
     * [level]이 [Level.CONNECTED]가 아니면 [IllegalStateException]을 던진다.
     */
    val services: List<Service>

    /**
     * MTU(Maximum Transmission Unit) 병경하도록 요청한다.
     *
     *
     * @see Connection.mtu
     */
    suspend fun requestMtu(mtu: Int = MTU_DEFAULT): Int

    /**
     * 서비스 목록을 갱신하고 반환한다.
     */
    suspend fun services(): List<Service>

    fun disconnect()
}
