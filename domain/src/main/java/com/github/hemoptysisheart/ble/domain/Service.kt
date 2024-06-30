package com.github.hemoptysisheart.ble.domain

import com.github.hemoptysisheart.ble.spec.core.Service

/**
 * [Connection]에 있는 서비스.
 */
interface Service {
    /**
     * 서비스 종류.
     */
    val type: Service

    val characteristics: List<Characteristic>
}
