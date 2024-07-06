package com.github.hemoptysisheart.ble.domain

interface Descriptor {
    val type: com.github.hemoptysisheart.ble.spec.core.Descriptor

    val readable: Boolean

    val writable: Boolean
}
