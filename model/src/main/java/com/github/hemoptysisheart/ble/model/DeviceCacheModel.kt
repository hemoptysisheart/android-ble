package com.github.hemoptysisheart.ble.model

import com.github.hemoptysisheart.ble.domain.Device
import java.time.Duration
import java.time.Instant

interface DeviceCacheModel {
    companion object {
        /**
         * 캐시 유효기간 최소값. milliseconds 단위.
         */
        const val TTL_MIN = 1000L

        /**
         * 캐시 유효기간 기본값. milliseconds 단위.
         */
        const val TTL_DEFAULT = 60 * TTL_MIN

        /**
         * 캐시 유효기간 최대값. milliseconds 단위.
         */
        const val TTL_MAX = 10 * TTL_DEFAULT
    }

    /**
     * 캐시 유효기간.
     */
    val ttl: Duration

    /**
     * 캐시 만료 시각.
     *
     * @see Instant.MIN 캐시가 비어있을 때.
     */
    val expireAt: Instant

    /**
     * 캐시된 디바이스 목록.
     */
    val devices: List<Device>

    /**
     * 캐시된 기기가 있으면 `true`, 비어있으면 `false`.
     */
    val cached: Boolean

    /**
     * 캐시를 갱신한다.
     */
    fun cache(devices: List<Device>)

    /**
     * 주소로 캐시된 디바이스를 찾는다.
     */
    operator fun get(address: String): Device?

    /**
     * 캐시를 비운다.
     */
    fun clear()
}
