package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGattService
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomService
import com.github.hemoptysisheart.ble.spec.core.Service

class Service(
    val key: String,
    private val gatt: GattWrapper,
    private val target: BluetoothGattService,
) : com.github.hemoptysisheart.ble.domain.Service {
    val tag = "Service/$key"

    override val type: Service = Service(target.uuid)
        ?: CustomService(target.uuid)

    override val characteristics: List<Characteristic> = target.characteristics
        .map { Characteristic(key, gatt, it, this@Service) }

    override fun toString() = listOf(
        "target=$target",
        "type=$type",
        "characteristics=$characteristics"
    ).joinToString(", ", "Service(", ")")
}
