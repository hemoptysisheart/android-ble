package com.github.hemoptysisheart.ble.model

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.os.Build

class ScanModelImpl(
    private val permissionModel: PermissionModel
) : ScanModel {
    override val permission: List<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(BLUETOOTH_SCAN, BLUETOOTH_CONNECT)
    } else {
        listOf(ACCESS_FINE_LOCATION)
    }

    override val granted: Boolean
        get() = permissionModel.hasAllPermission(*permission.toTypedArray())
}
