package com.github.hemoptysisheart.ble.model

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.bluetooth.BluetoothClass.Service
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.defaultToString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScanModelImpl(
    private val permissionModel: PermissionModel,
    private val deviceCacheModel: DeviceCacheModel,
    private val scanner: BluetoothLeScanner,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ScanModel {
    companion object {
        private const val TAG = "ScanModelImpl"
    }

    private val devices = mutableSetOf<Device>()

    private val callback = object : ScanCallback() {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
        private fun handle(result: ScanResult) {
            devices.add(Device(result.device, result.rssi))

            // 서비스 정보가 있는지 확인.
            if (true == result.device.name?.isNotEmpty()) {
                result.device.bluetoothClass?.apply {
                    val limitedDiscoverability = hasService(Service.LIMITED_DISCOVERABILITY)
                    val leAudio = hasService(Service.LE_AUDIO)
                    val positioning = hasService(Service.POSITIONING)
                    val networking = hasService(Service.NETWORKING)
                    val render = hasService(Service.RENDER)
                    val capture = hasService(Service.CAPTURE)
                    val objectTransfer = hasService(Service.OBJECT_TRANSFER)
                    val audio = hasService(Service.AUDIO)
                    val telephony = hasService(Service.TELEPHONY)
                    val information = hasService(Service.INFORMATION)
                    val hasService = listOf(
                        limitedDiscoverability,
                        leAudio,
                        positioning,
                        networking,
                        render,
                        capture,
                        objectTransfer,
                        audio,
                        telephony,
                        information
                    ).any { it }

                    if (hasService) {
                        Log.i(
                            TAG,
                            listOf(
                                "result=$result",
                                "device=${result.device}",
                                "limitedDiscoverability=$limitedDiscoverability",
                                "leAudio=$leAudio",
                                "positioning=$positioning",
                                "networking=$networking",
                                "render=$render",
                                "capture=$capture",
                                "objectTransfer=$objectTransfer",
                                "audio=$audio",
                                "telephony=$telephony",
                                "information=$information"
                            ).joinToString(", ", "#callback.onScanResult service check. : ")
                        )
                    }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            Log.v(TAG, "#callback.onScanResult args : callbackType=$callbackType, result=$result")

            if (null != result) {
                handle(result)
            }
        }

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            Log.v(TAG, "#callback.onBatchScanResults args : results=$results")

            for (result in results ?: emptyList()) {
                handle(result)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.v(TAG, "#callback.onScanFailed args : errorCode=$errorCode")
        }
    }

    override val permission: List<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        listOf(BLUETOOTH_SCAN, BLUETOOTH_CONNECT)
    } else {
        listOf(ACCESS_FINE_LOCATION)
    }

    override val granted: Boolean
        get() = permissionModel.hasAllPermission(*permission.toTypedArray())

    override var scanning = false
        private set

    @RequiresPermission("android.permission.BLUETOOTH_SCAN")
    override suspend fun scan(timeout: Long): List<com.github.hemoptysisheart.ble.domain.Device> {
        when {
            !granted ->
                throw IllegalStateException("Permission not granted.")

            scanning ->
                throw IllegalStateException("Already scanning.")
        }

        return withContext(dispatcher) {
            val settings = ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build()
            scanner.startScan(null, settings, callback)

            launch {
                delay(timeout)
                scanner.stopScan(callback)
            }.join()

            val result = devices.toMutableList()
            devices.clear()
            result.sort()
            deviceCacheModel.cache(result)
            result
        }
    }

    override fun toString() = listOf(
        "permission=${permissionModel.defaultToString()}",
        "deviceCacheModel=${deviceCacheModel.defaultToString()}"
    ).joinToString(", ", "$TAG(", ")")
}
