package com.github.hemoptysisheart.ble.model

import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.spec.core.Service

class Service(
    override val type: Service,
    override val characteristics: List<Characteristic>
) : com.github.hemoptysisheart.ble.domain.Service {
    override fun toString() = listOf(
        "type=$type"
    ).joinToString(", ", "Service(", ")")
}
