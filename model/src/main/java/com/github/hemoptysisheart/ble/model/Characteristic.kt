package com.github.hemoptysisheart.ble.model

import com.github.hemoptysisheart.ble.spec.core.Characteristic

class Characteristic(
    override val type: Characteristic
) : com.github.hemoptysisheart.ble.domain.Characteristic {
    override fun toString() = listOf(
        "type=$type"
    ).joinToString(", ", "Characteristic(", ")")
}
