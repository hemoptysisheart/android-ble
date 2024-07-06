package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE
import com.github.hemoptysisheart.ble.spec.core.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomCharacteristic

class Characteristic(
    @Suppress("MemberVisibilityCanBePrivate")
    val source: BluetoothGattCharacteristic,
) : com.github.hemoptysisheart.ble.domain.Characteristic {
    override val type: Characteristic = Characteristic(source.uuid)
        ?: CustomCharacteristic(source.uuid)
    override val readable: Boolean
        get() = source.properties and PROPERTY_READ != 0

    override val writable: Boolean
        get() = source.properties and PROPERTY_WRITE != 0

    override val writableWithoutResponse: Boolean
        get() = source.properties and PROPERTY_WRITE_NO_RESPONSE != 0

    override fun toString() = listOf(
        "source=$source",
        "type=$type",
        "readable=$readable",
        "writable=$writable",
        "writableWithoutResponse=$writableWithoutResponse"
    ).joinToString(", ", "Characteristic(", ")")
}
