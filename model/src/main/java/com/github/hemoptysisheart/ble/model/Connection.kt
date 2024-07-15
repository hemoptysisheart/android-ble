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
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.domain.Connection.State
import com.github.hemoptysisheart.ble.domain.Service
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class Connection(
    /**
     * 태그 접미사.
     */
    private val tag: String,
    private val scope: CoroutineScope,
    builder: (BluetoothGattCallback) -> BluetoothGatt
) : Connection {
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
            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=${"0x%02X".format(status)}($status)")
            }

            mtuDeferred!!.complete(mtu)
        }
    }

    private val _state = MutableStateFlow(State(Level.DISCONNECTED))
    override val state: StateFlow<State> = _state

    override var level: Level = Level.DISCONNECTED
        protected set(value) {
            Log.d(tag, "#level.set : $value")

            field = value
            _state.update { it.copy(level = value) }
        }

    override var mtu: Int? = null
        private set(value) {
            Log.d(tag, "#mtu.set : $value")

            field = value
            _state.update { it.copy(mtu = value) }
        }

    private var _services: List<Service>? = null
        set(value) {
            field = value
            _state.update { it.copy(services = value) }
        }

    override val services: List<Service>
        get() = if (null == _services) {
            emptyList()
        } else {
            _services!!
        }

    private val gatt = builder(callback)

    private var mtuDeferred: CompletableDeferred<Int>? = null


    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override suspend fun requestMtu(mtu: Int): Int {
        Log.d(tag, "#requestMtu args : mtu=$mtu")

        mtuDeferred = CompletableDeferred()
        gatt.requestMtu(mtu)
        this.mtu = mtuDeferred!!.await()
        mtuDeferred = null
        return this.mtu!!
    }

    override fun toString() = listOf(
        "state=${state.value}"
    ).joinToString(", ", "$tag(", ")")
}
