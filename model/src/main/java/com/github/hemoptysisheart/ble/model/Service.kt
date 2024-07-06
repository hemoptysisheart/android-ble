package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGattService
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomService
import com.github.hemoptysisheart.ble.spec.core.Service

class Service(
    private val source: BluetoothGattService,
) : com.github.hemoptysisheart.ble.domain.Service {
    override val type: Service = Service(source.uuid)
        ?: CustomService(source.uuid)

    override val characteristics: List<Characteristic> = source.characteristics
        .map { Characteristic(it) }

    override fun toString() = listOf(
        "source=$source",
        "type=$type",
        "characteristics=$characteristics"
    ).joinToString(", ", "Service(", ")")
}
