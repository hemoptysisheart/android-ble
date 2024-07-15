package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGattDescriptor
import com.github.hemoptysisheart.ble.spec.core.CustomDescriptor

class Descriptor(
    internal val target: BluetoothGattDescriptor,
) : com.github.hemoptysisheart.ble.domain.Descriptor {
    private val tag = "Descriptor"

    override val type = com.github.hemoptysisheart.ble.spec.core.Descriptor(target.uuid)
        ?: CustomDescriptor(target.uuid)

    val readable: Boolean
        get() = target.permissions.let {
            it and BluetoothGattDescriptor.PERMISSION_READ != 0 ||
                    it and BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED != 0 ||
                    it and BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED_MITM != 0
        }


    val writable: Boolean
        get() = target.permissions.let {
            it and BluetoothGattDescriptor.PERMISSION_WRITE != 0 ||
                    it and BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED != 0 ||
                    it and BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED_MITM != 0 ||
                    it and BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED != 0
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
