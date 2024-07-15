package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothProfile.STATE_CONNECTED
import android.bluetooth.BluetoothProfile.STATE_CONNECTING
import android.bluetooth.BluetoothProfile.STATE_DISCONNECTED
import android.bluetooth.BluetoothProfile.STATE_DISCONNECTING
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.domain.toHexaString
import kotlinx.coroutines.CompletableDeferred

/**
 * [BluetoothGatt]와 [BluetoothGattCallback]을 래핑해서 `suspend fun` 함수로 제공한다.
 */
class GattWrapper(
    internal val id: String,
    private val builder: (BluetoothGattCallback) -> BluetoothGatt
) {
    private val callback = object : BluetoothGattCallback() {
        override fun onPhyUpdate(gatt: BluetoothGatt?, txPhy: Int, rxPhy: Int, status: Int) {
            Log.v(tag, "#callback.onPhyUpdate args : gatt=$gatt, txPhy=$txPhy, rxPhy=$rxPhy, status=$status")
            gatt!!
            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=$status")
            }

            // TODO
        }

        override fun onPhyRead(gatt: BluetoothGatt?, txPhy: Int, rxPhy: Int, status: Int) {
            Log.v(tag, "#callback.onPhyRead args : gatt=$gatt, txPhy=$txPhy, rxPhy=$rxPhy, status=$status")
            gatt!!
            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=$status")
            }

            // TODO
        }

        @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            Log.i(
                tag,
                listOf(
                    "gatt=$gatt",
                    "status=$status(${"0x%X".format(status)})",
                    "newState=$newState(${"0x%X".format(newState)})"
                ).joinToString(", ", "#callback.onConnectionStateChange args : ")
            )
            require(null != gatt) { "gatt is required" }

            if (this@GattWrapper.gatt !== gatt) {
                Log.e(
                    tag,
                    listOf(
                        "gatt=$gatt",
                        "this.gatt=${this@GattWrapper.gatt}"
                    ).joinToString(", ", "#callback.onConnectionStateChange gatt does not match : ")
                )
            }

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

            level?.complete(newLevel)
                ?: {
                    Log.e(tag, "#callback.onConnectionStateChange level is not set.")
                    throw IllegalStateException("level is not set")
                }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            Log.v(tag, "#callback.onServicesDiscovered args : gatt=$gatt, status=$status")
            require(null != gatt) { "gatt is required" }

            if (this@GattWrapper.gatt !== gatt) {
                Log.e(
                    tag,
                    listOf(
                        "gatt=$gatt",
                        "this.gatt=${this@GattWrapper.gatt}"
                    ).joinToString(", ", "#callback.onServicesDiscovered gatt does not match : ")
                )
            }

            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=$status")
            }

            val services = gatt!!.services.map { Service(target = it, gatt = this@GattWrapper) }
            Log.d(tag, "#callback.onServicesDiscovered : services=${services}")
            this@GattWrapper.services?.complete(services)
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int
        ) {
            Log.v(
                tag,
                listOf(
                    "gatt=$gatt",
                    "characteristic=$characteristic",
                    "value=${value.toList()}",
                    "status=$status"
                ).joinToString(", ", "#callback.onCharacteristicRead args : ")
            )

            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=$status")
            }

            val read = characteristicRead[characteristic]
            if (null == read) {
                Log.e(tag, "#callback.onCharacteristicRead read is not set : characteristic=${characteristic}")
                throw IllegalStateException("read is not set")
            }
            read.complete(value)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            Log.i(
                tag,
                listOf(
                    "gatt=$gatt",
                    "characteristic=$characteristic",
                    "value=${value.toList()}"
                ).joinToString(", ", "#callback.onCharacteristicChanged args : ")
            )

//            val read = characteristicRead[characteristic]
//            if (null == read) {
//                Log.e(tag, "#callback.onCharacteristicChanged read is not set : characteristic=${characteristic}")
//                throw IllegalStateException("read is not set")
//            }
//            read.complete(value)
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            Log.v(
                tag,
                listOf(
                    "gatt=$gatt",
                    "characteristic=$characteristic",
                    "status=$status"
                ).joinToString(", ", "#callback.onCharacteristicWrite args : ")
            )

            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=$status")
            }

            // TODO
        }

        override fun onDescriptorRead(
            gatt: BluetoothGatt,
            descriptor: BluetoothGattDescriptor,
            status: Int,
            value: ByteArray
        ) {
            Log.v(
                tag,
                listOf(
                    "gatt=$gatt",
                    "descriptor=$descriptor",
                    "status=$status",
                    "value=${value.toList()}"
                ).joinToString(", ", "#callback.onDescriptorRead args : ")
            )

            // TODO
        }

        override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
            Log.v(
                tag,
                listOf(
                    "gatt=$gatt",
                    "descriptor=$descriptor",
                    "status=$status(${"0x%X".format(status)})"
                ).joinToString(", ", "#callback.onDescriptorWrite args : ")
            )
            require(null != gatt) { "gatt is required" }
            require(null != descriptor) { "descriptor is required" }

            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=${"0x%02X".format(status)}($status)")
            }

            descriptorWrite[descriptor]?.complete(Unit)
                ?: run {
                    Log.e(tag, "#callback.onDescriptorWrite descriptorWrite is not set : descriptor=${descriptor}")
                    throw IllegalStateException("descriptorWrite is not set")
                }
            descriptorWrite.remove(descriptor)
        }

        override fun onReliableWriteCompleted(gatt: BluetoothGatt?, status: Int) {
            Log.v(
                tag,
                listOf(
                    "gatt=$gatt",
                    "status=$status"
                ).joinToString(", ", "#callback.onReliableWriteCompleted args : ")
            )

            // TODO
        }

        override fun onReadRemoteRssi(gatt: BluetoothGatt?, rssi: Int, status: Int) {
            Log.v(
                tag,
                listOf(
                    "gatt=$gatt",
                    "rssi=$rssi",
                    "status=$status"
                ).joinToString(", ", "#callback.onReadRemoteRssi args : ")
            )

            // TODO
        }

        override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) {
            Log.v(tag, "#callback.onMtuChanged args : gatt=$gatt, mtu=$mtu, status=$status")
            require(null != gatt) { "gatt is required" }

            if (BluetoothGatt.GATT_SUCCESS != status) {
                throw IllegalArgumentException("status is not success : status=$status")
            }

            this@GattWrapper.mtu!!.complete(mtu)
        }

        override fun onServiceChanged(gatt: BluetoothGatt) {
            Log.v(tag, "#callback.onServiceChanged args : gatt=$gatt")

            // TODO
        }
    }

    internal lateinit var gatt: BluetoothGatt

    private var level: CompletableDeferred<Level>? = null
    private var mtu: CompletableDeferred<Int>? = null
    private var services: CompletableDeferred<List<Service>>? = null
    private val characteristicRead = mutableMapOf<BluetoothGattCharacteristic, CompletableDeferred<ByteArray>>()
    private val descriptorWrite = mutableMapOf<BluetoothGattDescriptor, CompletableDeferred<Unit>>()

    val tag = "GattWrapper/$id"

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    suspend fun connect(): Level {
        Log.d(tag, "#connect called.")
        if (null != level) {
            throw IllegalStateException("level is already set : level=${level}")
        }

        level = CompletableDeferred()

        gatt = builder(callback)

        return level?.await()
            ?: throw IllegalStateException("level is not set")
    }

    suspend fun setMtu(mtu: Int): Int {
        Log.d(tag, "#setMtu args : mtu=$mtu")
        when {
            null == level || Level.CONNECTED != level?.await() ->
                throw IllegalStateException("level is not connected : level=${level?.await()}")

            null != this.mtu ->
                throw IllegalStateException("mtu is not set : mtu=${this.mtu}")
        }

        this.mtu = CompletableDeferred()
        this.mtu!!.complete(mtu)
        return this.mtu!!.await()
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    suspend fun services(): List<Service> {
        when {
            null == level || Level.CONNECTED != level?.await() ->
                throw IllegalStateException("level is not connected : level=${level?.await()}")

            null != services ->
                throw IllegalStateException("services is already set : services=${services}")
        }

        services = CompletableDeferred()
        gatt.discoverServices()
        return services!!.await()
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    suspend fun disconnect(): Level {
        Log.d(tag, "#disconnect called.")
        when {
            null == level ->
                throw IllegalStateException("level is not set")

            Level.CONNECTED != level?.await() ->
                throw IllegalStateException("level is not connected : level=${level?.await()}")
        }

        level = CompletableDeferred()
        gatt.disconnect()
        val lv = level?.await()
            ?: throw IllegalStateException("level is not set")

        level!!.cancel()
        level = null

        services!!.cancel()
        services = null

        mtu!!.cancel()
        mtu = null

        characteristicRead.clear()

        return lv
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    suspend fun read(characteristic: Characteristic): ByteArray {
        Log.v(tag, "#read args : characteristic=$characteristic")

        when {
            null == level || Level.CONNECTED != level?.await() ->
                throw IllegalStateException("level is not connected : level=${level?.await()}")

            characteristicRead.containsKey(characteristic.target) ->
                throw IllegalStateException("characteristic is already in read : characteristic=${characteristic}")
        }

        val read = CompletableDeferred<ByteArray>()
        characteristicRead[characteristic.target] = read

        gatt.readCharacteristic(characteristic.target)
        val bytes = read.await()
        characteristicRead.remove(characteristic.target)

        Log.d(tag, "#read return : ${bytes.toHexaString()}")
        return bytes
    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    suspend fun write(descriptor: Descriptor, value: ByteArray) {
        Log.d(tag, "#write args : descriptor=$descriptor, value=${value.toHexaString()}")

        when {
            null == level || Level.CONNECTED != level?.await() ->
                throw IllegalStateException("level is not connected : level=${level?.await()}")

            descriptorWrite.containsKey(descriptor.target) ->
                throw IllegalStateException("descriptor is already in write : descriptor=${descriptor}")
        }

        val write = CompletableDeferred<Unit>()
        descriptorWrite[descriptor.target] = write

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            gatt.writeDescriptor(descriptor.target, value)
        } else {
            descriptor.target.value = value
            gatt.writeDescriptor(descriptor.target)
        }

        write.await()
        descriptorWrite.remove(descriptor.target)
    }
}