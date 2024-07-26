package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGattDescriptor
import android.util.Log
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.domain.toHexaString
import com.github.hemoptysisheart.ble.spec.core.CustomDescriptor

class Descriptor(
    val key: String,
    private val gatt: GattWrapper,
    /**
     * Android 시스템이 제공하는 디스크립터.
     */
    internal val target: BluetoothGattDescriptor,
    override val characteristic: Characteristic,
) : com.github.hemoptysisheart.ble.domain.Descriptor {
    private val tag = "Descriptor/$key"

    override val type = com.github.hemoptysisheart.ble.spec.core.Descriptor(target.uuid)
        ?: CustomDescriptor(target.uuid)

    override val readable: Boolean
        get() = target.permissions.let {
            it and BluetoothGattDescriptor.PERMISSION_READ != 0 ||
                    it and BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED != 0 ||
                    it and BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED_MITM != 0
        }


    override val writable: Boolean
        get() = target.permissions.let {
            it and BluetoothGattDescriptor.PERMISSION_WRITE != 0 ||
                    it and BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED != 0 ||
                    it and BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED_MITM != 0 ||
                    it and BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED != 0
        }

    override suspend fun write(value: ByteArray) {
        Log.v(tag, "#write args : value=${value.toHexaString()}")
    }

    override fun equals(other: Any?) = this === other || (
            other is Descriptor &&
                    target == other.target &&
                    type == other.type
            )

    override fun hashCode(): Int {
        var result = target.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

    override fun toString() = listOf(
        "target=$target",
        "type=$type",
        "readable=$readable",
        "writable=$writable"
    ).joinToString(", ", "Descriptor(", ")")
}
