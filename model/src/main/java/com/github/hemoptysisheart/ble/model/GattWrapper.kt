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
import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.Connection.Level
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class GattWrapper(
    val key: String,
    private val scope: CoroutineScope,
    builder: (BluetoothGattCallback) -> BluetoothGatt
) {
    /**
     * 채널에 값을 전달한다.
     */
    private fun <T> Channel<T>.produce(value: T) {
        scope.launch {
            send(value)
        }
    }

    private val callback = object : BluetoothGattCallback() {
        override fun onPhyUpdate(gatt: BluetoothGatt?, txPhy: Int, rxPhy: Int, status: Int) {
            Log.v(tag, "#callback.onPhyUpdate args : gatt=$gatt, txPhy=$txPhy, rxPhy=$rxPhy, status=$status")
            gatt!!
        }

        override fun onPhyRead(gatt: BluetoothGatt?, txPhy: Int, rxPhy: Int, status: Int) {
            Log.v(tag, "#callback.onPhyRead args : gatt=$gatt, txPhy=$txPhy, rxPhy=$rxPhy, status=$status")
            gatt!!
        }

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            Log.v(tag, "#callback.onConnectionStateChange args : gatt=$gatt, status=$status, newState=$newState")
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
            levelChannel.produce(newLevel)
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            Log.v(tag, "#callback.onServicesDiscovered args : gatt=$gatt, status=$status")
            gatt!!
            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=${"0x%02X".format(status)}($status)")
            }

            val services = gatt.services.map {
                Service("Service/$key", it)
            }
            servicesChannel.produce(services)
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            Log.v(
                tag,
                "#callback.onCharacteristicWrite args : gatt=$gatt, characteristic=$characteristic, status=$status"
            )
            gatt!!
        }

        override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
            Log.v(tag, "#callback.onDescriptorWrite args : gatt=$gatt, descriptor=$descriptor, status=$status")
            gatt!!
        }

        override fun onReliableWriteCompleted(gatt: BluetoothGatt?, status: Int) {
            Log.v(tag, "#callback.onReliableWriteCompleted args : gatt=$gatt, status=$status")
            gatt!!
        }

        override fun onReadRemoteRssi(gatt: BluetoothGatt?, rssi: Int, status: Int) {
            Log.v(tag, "#callback.onReadRemoteRssi args : gatt=$gatt, rssi=$rssi, status=$status")
            gatt!!
        }

        override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) {
            Log.v(tag, "#callback.onMtuChanged args : gatt=$gatt, mtu=$mtu, status=$status")
            gatt!!
            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=${"0x%02X".format(status)}($status)")
            }

            this@GattWrapper.mtu = mtu
            mtuChannel.produce(mtu)
        }
    }

    private val tag: String = "GattWrapper/$key"
    private val gatt = builder(callback)

    private val levelChannel: Channel<Level> = Channel(1)
    var level: Level = Level.DISCONNECTED
        private set

    private val mtuChannel: Channel<Int> = Channel(1)
    var mtu: Int? = null
        private set

    private val servicesChannel: Channel<List<Service>> = Channel(1)
    var services: List<Service>? = null
        private set

    suspend fun awaitConnected() {
        when (levelChannel.receive()) {
            Level.CONNECTED ->
                return

            Level.DISCONNECTED ->
                throw IllegalStateException("disconnected : level=$level")

            Level.CONNECTING ->
                return

            Level.DISCONNECTING ->
                throw IllegalStateException("disconnecting : level=$level")
        }
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    suspend fun requestMtu(mtu: Int): Int {
        if (Level.CONNECTED != level) {
            throw IllegalStateException("not connected : level=$level")
        }

        gatt.requestMtu(mtu)
        return mtuChannel.receive()
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    suspend fun discoverServices(): List<Service> {
        if (Level.CONNECTED != level) {
            throw IllegalStateException("not connected : level=$level")
        }

        gatt.discoverServices()
        return servicesChannel.receive()
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    fun disconnect() {
        gatt.disconnect()
    }

    override fun toString() = listOf(
        "level=$level",
        "mtu=$mtu",
        "services=$services"
    ).joinToString(", ", "$tag(", ")")
}