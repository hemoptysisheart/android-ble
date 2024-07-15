package com.github.hemoptysisheart.ble.ui.state

import androidx.compose.runtime.Immutable
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import com.github.hemoptysisheart.ble.spec.core.MajorServiceClass

/**
 * [com.github.hemoptysisheart.ble.domain.Device]의 상태.
 */
@Immutable
data class DeviceState(
    /**
     * 기기 이름.
     */
    val name: String?,

    /**
     * 기기 주소.
     */
    val address: String,

    /**
     * 기기 종류.
     */
    val category: DeviceClass,

    /**
     * 기기가 제공하는 서비스.
     */
    val services: List<MajorServiceClass>,

    /**
     * 기기 신호 세기.
     */
    val rssi: Int,

    /**
     * 기기 연결.
     */
    val connection: Connection.State?
)
