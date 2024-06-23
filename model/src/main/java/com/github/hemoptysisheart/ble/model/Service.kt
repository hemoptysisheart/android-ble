package com.github.hemoptysisheart.ble.model

import com.github.hemoptysisheart.ble.spec.core.Service

class Service(
    override val type: Service
) : com.github.hemoptysisheart.ble.domain.Service {
    override fun toString() = listOf(
        "type=$type"
    ).joinToString(", ", "Service(", ")")
}
