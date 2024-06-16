package com.github.hemoptysisheart.ble.model

interface ScanModel {
    val permission: List<String>

    val granted: Boolean
}
