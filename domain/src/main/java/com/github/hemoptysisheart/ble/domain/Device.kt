package com.github.hemoptysisheart.ble.domain

import com.github.hemoptysisheart.ble.spec.core.DeviceClass

/**
 * Bluetooth LE 기기.
 */
interface Device {
    /**
     * 기기 이름.
     */
    val name: String?

    /**
     * 기기 주소.
     */
    val address: String

    /**
     * 기기 종류.
     */
    val category: DeviceClass
}
