package com.github.hemoptysisheart.ble.domain

/**
 * 기기 연결 상태.
 *
 * [CONNECTING] -> [CONNECTED] -> [DISCONNECTING] -> [DISCONNECTED] 순으로 변경된다.
 */
enum class ConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    DISCONNECTING
}
