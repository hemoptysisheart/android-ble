package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE
import com.github.hemoptysisheart.ble.domain.Descriptor
import com.github.hemoptysisheart.ble.domain.Descriptor.Companion.UUID_CLIENT_CHARACTERISTIC_CONFIGURATION
import com.github.hemoptysisheart.ble.domain.Service
import com.github.hemoptysisheart.ble.spec.core.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomCharacteristic

class Characteristic(
    val key: String,
    private val gatt: GattWrapper,
    /**
     * Android 시스템이 제공하는 캐릭터리스틱.
     */
    internal val target: BluetoothGattCharacteristic,
    override val service: Service
) : com.github.hemoptysisheart.ble.domain.Characteristic {
    private val tag = "Characteristic/$key"

    override val type: Characteristic = Characteristic(target.uuid)
        ?: CustomCharacteristic(target.uuid)

    val readable: Boolean
        get() = target.properties and PROPERTY_READ != 0

    val writable: Boolean
        get() = target.properties and PROPERTY_WRITE != 0

    val writableWithoutResponse: Boolean
        get() = target.properties and PROPERTY_WRITE_NO_RESPONSE != 0

    val indicatable: Boolean
        get() = target.properties and BluetoothGattCharacteristic.PROPERTY_INDICATE != 0 &&
                descriptors.any { it.type.uuid == UUID_CLIENT_CHARACTERISTIC_CONFIGURATION }

    val notifiable: Boolean
        get() = target.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0 &&
                descriptors.any { it.type.uuid == UUID_CLIENT_CHARACTERISTIC_CONFIGURATION }

    override val descriptors: List<Descriptor> = target.descriptors
        .map { Descriptor(key, gatt, it, this@Characteristic) }

    override fun toString() = listOf(
        "target=$target",
        "type=$type",
        "readable=$readable",
        "writable=$writable",
        "writableWithoutResponse=$writableWithoutResponse",
        "indicatable=$indicatable",
        "notifiable=$notifiable",
        "descriptors=$descriptors"
    ).joinToString(", ", "Characteristic(", ")")
}
