package com.github.hemoptysisheart.ble.model

import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.Device

/**
 * Bluetooth LE 기기 검색 모델.
 */
interface ScanModel {
    companion object {
        /**
         * 기본 기기 검색 시간 제한.
         */
        const val SCAN_TIMEOUT = 10_000L
    }

    /**
     * 필요한 권한 목록.
     */
    val permission: List<String>

    /**
     * 필요한 권한이 모두 허용되었는지 여부.
     */
    val granted: Boolean

    /**
     * 기기 검색 중 여부.
     */
    val scanning: Boolean

    /**
     * 기기 검색.
     *
     * @param timeout 기기 검색 시간 제한.
     *
     * @throws IllegalStateException 권한이 없거나 이미 검색 중일 때.
     */
    @RequiresPermission("android.permission.BLUETOOTH_SCAN")
    @Throws(IllegalStateException::class)
    suspend fun scan(timeout: Long = SCAN_TIMEOUT): List<Device>
}
