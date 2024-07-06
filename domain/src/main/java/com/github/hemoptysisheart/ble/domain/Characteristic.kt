package com.github.hemoptysisheart.ble.domain

interface Characteristic {
    val type: com.github.hemoptysisheart.ble.spec.core.Characteristic

    val readable: Boolean

    val writable: Boolean

    val writableWithoutResponse: Boolean
}
