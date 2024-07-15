package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothProfile.STATE_CONNECTED
import android.bluetooth.BluetoothProfile.STATE_CONNECTING
import android.bluetooth.BluetoothProfile.STATE_DISCONNECTED
import android.bluetooth.BluetoothProfile.STATE_DISCONNECTING
import android.util.Log
import com.github.hemoptysisheart.ble.domain.AbstractConnection
import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.domain.Service

class Connection(
    /**
     * 태그 접미사.
     */
    tag: String,
    builder: (BluetoothGattCallback) -> BluetoothGatt
) : AbstractConnection<Device>("Connection/$tag") {
    private val callback = object : BluetoothGattCallback() {
        override fun onPhyUpdate(gatt: BluetoothGatt?, txPhy: Int, rxPhy: Int, status: Int) {
            Log.v(tag, "#onPhyUpdate args : gatt=$gatt, txPhy=$txPhy, rxPhy=$rxPhy, status=$status")
            gatt!!
        }

        override fun onPhyRead(gatt: BluetoothGatt?, txPhy: Int, rxPhy: Int, status: Int) {
            Log.v(tag, "#onPhyRead args : gatt=$gatt, txPhy=$txPhy, rxPhy=$rxPhy, status=$status")
            gatt!!
        }

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            Log.v(tag, "#onConnectionStateChange args : gatt=$gatt, status=$status, newState=$newState")
            gatt!!
            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=${"0x%02X".format(status)}($status)")
            }

            val newLevel = when (newState) {
                STATE_DISCONNECTED ->
                    Level.DISCONNECTED

                STATE_CONNECTING ->
                    Level.CONNECTING

                STATE_CONNECTED ->
                    Level.CONNECTED

                STATE_DISCONNECTING ->
                    Level.DISCONNECTING

                else ->
                    throw IllegalArgumentException("unsupported newState : newState=$newState")
            }
            Log.i(tag, "#callback.onConnectionStateChange : newLevel=$newLevel")

            level = newLevel
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            Log.v(tag, "#onServicesDiscovered args : gatt=$gatt, status=$status")
            gatt!!
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            Log.v(tag, "#onCharacteristicWrite args : gatt=$gatt, characteristic=$characteristic, status=$status")
            gatt!!
        }

        override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
            Log.v(tag, "#onDescriptorWrite args : gatt=$gatt, descriptor=$descriptor, status=$status")
            gatt!!
        }

        override fun onReliableWriteCompleted(gatt: BluetoothGatt?, status: Int) {
            Log.v(tag, "#onReliableWriteCompleted args : gatt=$gatt, status=$status")
            gatt!!
        }

        override fun onReadRemoteRssi(gatt: BluetoothGatt?, rssi: Int, status: Int) {
            Log.v(tag, "#onReadRemoteRssi args : gatt=$gatt, rssi=$rssi, status=$status")
            gatt!!
        }

        override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) {
            Log.v(tag, "#onMtuChanged args : gatt=$gatt, mtu=$mtu, status=$status")
            gatt!!
        }
    }

    private val gatt = builder(callback)

    override var _services: List<Service>?
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun toString() = listOf(
        "state=${state.value}"
    ).joinToString(", ", "$tag(", ")")
}
