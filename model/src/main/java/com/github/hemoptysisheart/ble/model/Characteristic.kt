package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE
import com.github.hemoptysisheart.ble.spec.core.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomCharacteristic

class Characteristic(
    private val target: BluetoothGattCharacteristic,
) : com.github.hemoptysisheart.ble.domain.Characteristic {
    override val type: Characteristic = Characteristic(target.uuid)
        ?: CustomCharacteristic(target.uuid)

    override val readable: Boolean
        get() = target.properties and PROPERTY_READ != 0

    override val writable: Boolean
        get() = target.properties and PROPERTY_WRITE != 0

    override val writableWithoutResponse: Boolean
        get() = target.properties and PROPERTY_WRITE_NO_RESPONSE != 0

    override fun toString() = listOf(
        "target=$target",
        "type=$type",
        "readable=$readable",
        "writable=$writable",
        "writableWithoutResponse=$writableWithoutResponse"
    ).joinToString(", ", "Characteristic(", ")")
}
