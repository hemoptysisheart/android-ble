package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattService
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomService
import com.github.hemoptysisheart.ble.spec.core.Service

class Service(
    private val target: BluetoothGattService,
    private val gatt: BluetoothGatt,
) : com.github.hemoptysisheart.ble.domain.Service {
    override val type: Service = Service(target.uuid)
        ?: CustomService(target.uuid)

    override val characteristics: List<Characteristic> = target.characteristics
        .map { Characteristic(target = it, gatt = gatt) }

    override fun toString() = listOf(
        "target=$target",
        "gatt=$gatt",
        "type=$type",
        "characteristics=$characteristics"
    ).joinToString(", ", "Service(", ")")
}
