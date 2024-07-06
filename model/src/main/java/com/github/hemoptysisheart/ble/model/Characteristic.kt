package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE
import com.github.hemoptysisheart.ble.spec.core.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomCharacteristic

class Characteristic(
    /**
     * Android 시스템이 제공하는 캐릭터리스틱.
     */
    private val target: BluetoothGattCharacteristic,
    private val gatt: BluetoothGatt
) : com.github.hemoptysisheart.ble.domain.Characteristic {
    override val type: Characteristic = Characteristic(target.uuid)
        ?: CustomCharacteristic(target.uuid)

    override val readable: Boolean
        get() = target.properties and PROPERTY_READ != 0

    override val writable: Boolean
        get() = target.properties and PROPERTY_WRITE != 0

    override val writableWithoutResponse: Boolean
        get() = target.properties and PROPERTY_WRITE_NO_RESPONSE != 0

    override suspend fun read(): ByteArray {
        TODO("Not yet implemented")
    }

    override fun toString() = listOf(
        "target=$target",
        "gatt=$gatt",
        "type=$type",
        "readable=$readable",
        "writable=$writable",
        "writableWithoutResponse=$writableWithoutResponse"
    ).joinToString(", ", "Characteristic(", ")")
}
