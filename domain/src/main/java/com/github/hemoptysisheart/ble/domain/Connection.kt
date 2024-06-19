package com.github.hemoptysisheart.ble.domain

import kotlinx.coroutines.flow.StateFlow

interface Connection {
    data class State(
        val connectionState: ConnectionState
    )

    val state: StateFlow<State>

    val device: Device

    /**
     * 기기 연결 상태.
     *
     * 기기에 연결하기 전에는 [Connection] 인스턴스 자체가 없기 때문에 [ConnectionState.CONNECTING] 상태로 시작한다.
     */
    val connectionState: ConnectionState
        get() = state.value.connectionState
}
