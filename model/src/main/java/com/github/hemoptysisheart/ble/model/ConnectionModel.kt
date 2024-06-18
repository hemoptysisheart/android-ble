package com.github.hemoptysisheart.ble.model

import com.github.hemoptysisheart.ble.domain.Connection

/**
 * BLE 연결 모델.
 */
interface ConnectionModel {
    /**
     * 기기에 연결하기.
     *
     * @param address 연결할 기기 주소.
     */
    fun connect(address: String): Connection
}
