package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE
import android.util.Log
import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.Descriptor
import com.github.hemoptysisheart.ble.domain.toHexaString
import com.github.hemoptysisheart.ble.spec.core.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomCharacteristic

class Characteristic(
    /**
     * Android 시스템이 제공하는 캐릭터리스틱.
     */
    internal val target: BluetoothGattCharacteristic,
    private val gatt: GattWrapper
) : com.github.hemoptysisheart.ble.domain.Characteristic {
    private val tag = "Characteristic/${target.uuid}"

    override val type: Characteristic = Characteristic(target.uuid)
        ?: CustomCharacteristic(target.uuid)

    override val readable: Boolean
        get() = target.properties and PROPERTY_READ != 0

    override val writable: Boolean
        get() = target.properties and PROPERTY_WRITE != 0

    override val writableWithoutResponse: Boolean
        get() = target.properties and PROPERTY_WRITE_NO_RESPONSE != 0

    override val descriptors: List<Descriptor> = target.descriptors.map { Descriptor(it) }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override suspend fun read(): ByteArray {
        val bytes = gatt.read(this)
        Log.d(tag, "#read : bytes=${bytes.toHexaString()}")
        return bytes
    }

    override fun toString() = listOf(
        "target=$target",
        "gatt=$gatt",
        "type=$type",
        "readable=$readable",
        "writable=$writable",
        "writableWithoutResponse=$writableWithoutResponse",
        "descriptors=$descriptors"
    ).joinToString(", ", "$tag(", ")")
}
