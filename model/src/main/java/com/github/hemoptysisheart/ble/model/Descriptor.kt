package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGattDescriptor
import android.util.Log
import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.toHexaString
import com.github.hemoptysisheart.ble.spec.core.CustomDescriptor

class Descriptor(
    internal val target: BluetoothGattDescriptor,
    private val gatt: GattWrapper
) : com.github.hemoptysisheart.ble.domain.Descriptor {
    private val tag = "Descriptor/${gatt.id}"

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

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override suspend fun write(value: ByteArray) {
        Log.d(tag, "#write : value=${value.toHexaString()}")
        gatt.write(this, value)
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
