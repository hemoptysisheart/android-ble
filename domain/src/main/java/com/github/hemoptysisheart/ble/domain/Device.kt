package com.github.hemoptysisheart.ble.domain

import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import com.github.hemoptysisheart.ble.spec.core.MajorServiceClass

/**
 * Bluetooth LE 기기.
 */
interface Device : Comparable<Device> {
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

    /**
     * 기기가 제공하는 서비스.
     */
    val services: List<MajorServiceClass>

    /**
     * 기기 신호 세기.
     */
    val rssi: Int

    /**
     * 기기 연결.
     */
    val connection: Connection?

    /**
     * 기기에 연결한다.
     */
    fun connect(): Connection

    /**
     * 기기 연결을 끊는다.
     */
    fun disconnect()

    override fun compareTo(other: Device): Int {
        var result = when {
            null != name && null == other.name ->
                -1

            null == name && null != other.name ->
                1

            null != name && null != other.name ->
                name!!.compareTo(other.name!!)

            else ->
                0
        }
        if (0 != result) {
            return result
        }

        result = category.compareTo(other.category)
        if (0 != result) {
            return result
        }

        return address.compareTo(other.address)
    }
}
