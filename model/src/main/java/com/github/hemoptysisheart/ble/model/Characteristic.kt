package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGattCharacteristic
import com.github.hemoptysisheart.ble.spec.core.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomCharacteristic

class Characteristic(
    @Suppress("MemberVisibilityCanBePrivate")
    val source: BluetoothGattCharacteristic,
) : com.github.hemoptysisheart.ble.domain.Characteristic {
    override fun toString() = listOf(
        "type=$type"
    ).joinToString(", ", "Characteristic(", ")")
}
