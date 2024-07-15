package com.github.hemoptysisheart.ble.model

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
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
    private val context: Context,
    private val permissionModel: PermissionModel,
    private val deviceCacheModel: DeviceCacheModel,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ScanModel {
    companion object {
        private const val TAG = "ScanModelImpl"
    }

    private val scanner: BluetoothLeScanner = (context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager)
        .adapter
        .bluetoothLeScanner
    private val devices = mutableSetOf<Device>()

    private val callback = object : ScanCallback() {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
        private fun handle(result: ScanResult) {
            result.log(TAG)
            devices.add(Device(context, result.rssi, result.device))
        }

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            Log.v(TAG, "#callback.onScanResult args : callbackType=$callbackType, result=$result")

            if (null == result) {
                Log.w(TAG, "#callback.onScanResult result is null.")
            } else {
                handle(result)
            }
        }

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            Log.v(TAG, "#callback.onBatchScanResults args : results=$results")

            if (null == results) {
                Log.w(TAG, "#callback.onBatchScanResults results is null.")
            } else for (result in results) {
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
