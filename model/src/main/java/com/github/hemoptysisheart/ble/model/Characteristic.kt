package com.github.hemoptysisheart.ble.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE
import android.bluetooth.BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
import android.bluetooth.BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
import android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
import android.util.Log
import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.Descriptor
import com.github.hemoptysisheart.ble.domain.Descriptor.Companion.UUID_CLIENT_CHARACTERISTIC_CONFIGURATION
import com.github.hemoptysisheart.ble.domain.toHexaString
import com.github.hemoptysisheart.ble.spec.core.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomCharacteristic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Characteristic(
    /**
     * Android 시스템이 제공하는 캐릭터리스틱.
     */
    internal val target: BluetoothGattCharacteristic,
    private val gatt: GattWrapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : com.github.hemoptysisheart.ble.domain.Characteristic {
    private val tag = "Characteristic/${gatt.id}"

    private val scope = CoroutineScope(dispatcher)
    private var notificationReadJob: Job? = null

    override val type: Characteristic = Characteristic(target.uuid)
        ?: CustomCharacteristic(target.uuid)

    override val readable: Boolean
        get() = target.properties and PROPERTY_READ != 0

    override val writable: Boolean
        get() = target.properties and PROPERTY_WRITE != 0

    override val writableWithoutResponse: Boolean
        get() = target.properties and PROPERTY_WRITE_NO_RESPONSE != 0

    override val indicatable: Boolean
        get() = target.properties and BluetoothGattCharacteristic.PROPERTY_INDICATE != 0 &&
                descriptors.any { it.type.uuid == UUID_CLIENT_CHARACTERISTIC_CONFIGURATION }

    override val notifiable: Boolean
        get() = target.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0 &&
                descriptors.any { it.type.uuid == UUID_CLIENT_CHARACTERISTIC_CONFIGURATION }

    @SuppressLint("MissingPermission")
    override val descriptors: List<Descriptor> = target.descriptors.map {
        Descriptor(it, gatt)
    }

    override suspend fun indication(enable: Boolean) {
        Log.d(tag, "#indication args : enable=$enable")

        if (!indicatable) {
            throw IllegalStateException("Indicatable is false.")
        }
        val cccd = descriptors.firstOrNull { it.type.uuid == UUID_CLIENT_CHARACTERISTIC_CONFIGURATION }
            ?: throw IllegalStateException("Client Characteristic Configuration descriptor does not found.")

        if (enable) {
            cccd.write(ENABLE_INDICATION_VALUE)
        } else {
            cccd.write(DISABLE_NOTIFICATION_VALUE)
        }
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override suspend fun notification(enable: Boolean) {
        Log.d(tag, "#notification args : enable=$enable")

        if (!notifiable) {
            throw IllegalStateException("Notifiable is false.")
        }

        val cccd = descriptors.firstOrNull { it.type.uuid == UUID_CLIENT_CHARACTERISTIC_CONFIGURATION }
            ?: throw IllegalStateException("Client Characteristic Configuration descriptor does not found.")

        if (enable) {
            cccd.write(ENABLE_NOTIFICATION_VALUE)
            notificationReadJob = scope.launch {
                while (true == notificationReadJob?.isActive) {
                    read()
                }
            }
        } else {
            cccd.write(DISABLE_NOTIFICATION_VALUE)
            notificationReadJob?.cancel()
        }
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override suspend fun read(): ByteArray {
        val bytes = gatt.read(this)
        Log.i(tag, "#read : bytes=${bytes.toHexaString()}")
        return bytes
    }

    override fun toString() = listOf(
        "target=$target",
        "gatt=$gatt",
        "type=$type",
        "readable=$readable",
        "writable=$writable",
        "writableWithoutResponse=$writableWithoutResponse",
        "indicatable=$indicatable",
        "notifiable=$notifiable",
        "descriptors=$descriptors"
    ).joinToString(", ", "$tag(", ")")
}
