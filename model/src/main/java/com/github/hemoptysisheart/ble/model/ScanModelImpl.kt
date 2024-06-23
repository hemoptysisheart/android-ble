package com.github.hemoptysisheart.ble.model

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.BLUETOOTH_CONNECT
import android.Manifest.permission.BLUETOOTH_SCAN
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VCR
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR
import android.bluetooth.BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET
import android.bluetooth.BluetoothClass.Device.COMPUTER_DESKTOP
import android.bluetooth.BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA
import android.bluetooth.BluetoothClass.Device.COMPUTER_LAPTOP
import android.bluetooth.BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA
import android.bluetooth.BluetoothClass.Device.COMPUTER_SERVER
import android.bluetooth.BluetoothClass.Device.COMPUTER_UNCATEGORIZED
import android.bluetooth.BluetoothClass.Device.COMPUTER_WEARABLE
import android.bluetooth.BluetoothClass.Device.HEALTH_BLOOD_PRESSURE
import android.bluetooth.BluetoothClass.Device.HEALTH_DATA_DISPLAY
import android.bluetooth.BluetoothClass.Device.HEALTH_GLUCOSE
import android.bluetooth.BluetoothClass.Device.HEALTH_PULSE_OXIMETER
import android.bluetooth.BluetoothClass.Device.HEALTH_PULSE_RATE
import android.bluetooth.BluetoothClass.Device.HEALTH_THERMOMETER
import android.bluetooth.BluetoothClass.Device.HEALTH_UNCATEGORIZED
import android.bluetooth.BluetoothClass.Device.HEALTH_WEIGHING
import android.bluetooth.BluetoothClass.Device.Major.AUDIO_VIDEO
import android.bluetooth.BluetoothClass.Device.Major.COMPUTER
import android.bluetooth.BluetoothClass.Device.Major.HEALTH
import android.bluetooth.BluetoothClass.Device.Major.IMAGING
import android.bluetooth.BluetoothClass.Device.Major.PERIPHERAL
import android.bluetooth.BluetoothClass.Device.Major.PHONE
import android.bluetooth.BluetoothClass.Device.Major.TOY
import android.bluetooth.BluetoothClass.Device.Major.UNCATEGORIZED
import android.bluetooth.BluetoothClass.Device.Major.WEARABLE
import android.bluetooth.BluetoothClass.Device.PERIPHERAL_KEYBOARD
import android.bluetooth.BluetoothClass.Device.PERIPHERAL_KEYBOARD_POINTING
import android.bluetooth.BluetoothClass.Device.PERIPHERAL_NON_KEYBOARD_NON_POINTING
import android.bluetooth.BluetoothClass.Device.PERIPHERAL_POINTING
import android.bluetooth.BluetoothClass.Device.PHONE_CELLULAR
import android.bluetooth.BluetoothClass.Device.PHONE_CORDLESS
import android.bluetooth.BluetoothClass.Device.PHONE_ISDN
import android.bluetooth.BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY
import android.bluetooth.BluetoothClass.Device.PHONE_SMART
import android.bluetooth.BluetoothClass.Device.PHONE_UNCATEGORIZED
import android.bluetooth.BluetoothClass.Device.TOY_CONTROLLER
import android.bluetooth.BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE
import android.bluetooth.BluetoothClass.Device.TOY_GAME
import android.bluetooth.BluetoothClass.Device.TOY_ROBOT
import android.bluetooth.BluetoothClass.Device.TOY_UNCATEGORIZED
import android.bluetooth.BluetoothClass.Device.TOY_VEHICLE
import android.bluetooth.BluetoothClass.Device.WEARABLE_GLASSES
import android.bluetooth.BluetoothClass.Device.WEARABLE_HELMET
import android.bluetooth.BluetoothClass.Device.WEARABLE_JACKET
import android.bluetooth.BluetoothClass.Device.WEARABLE_PAGER
import android.bluetooth.BluetoothClass.Device.WEARABLE_UNCATEGORIZED
import android.bluetooth.BluetoothClass.Device.WEARABLE_WRIST_WATCH
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
                    val majorClass = when (majorDeviceClass) {
                        PHONE -> "PHONE"
                        COMPUTER -> "COMPUTER"
                        AUDIO_VIDEO -> "AUDIO_VIDEO"
                        PERIPHERAL -> "PERIPHERAL"
                        IMAGING -> "IMAGING"
                        WEARABLE -> "WEARABLE"
                        TOY -> "TOY"
                        HEALTH -> "HEALTH"
                        UNCATEGORIZED -> "UNCATEGORIZED"
                        else -> "unknown"
                    }
                    val deviceClass = when (deviceClass) {
                        COMPUTER_UNCATEGORIZED -> "COMPUTER_UNCATEGORIZED"
                        COMPUTER_DESKTOP -> "COMPUTER_DESKTOP"
                        COMPUTER_SERVER -> "COMPUTER_SERVER"
                        COMPUTER_LAPTOP -> "COMPUTER_LAPTOP"
                        COMPUTER_HANDHELD_PC_PDA -> "COMPUTER_HANDHELD_PC_PDA"
                        COMPUTER_PALM_SIZE_PC_PDA -> "COMPUTER_PALM_SIZE_PC_PDA"
                        COMPUTER_WEARABLE -> "COMPUTER_WEARABLE"
                        PHONE_UNCATEGORIZED -> "PHONE_UNCATEGORIZED"
                        PHONE_CELLULAR -> "PHONE_CELLULAR"
                        PHONE_CORDLESS -> "PHONE_CORDLESS"
                        PHONE_SMART -> "PHONE_SMART"
                        PHONE_MODEM_OR_GATEWAY -> "PHONE_MODEM_OR_GATEWAY"
                        PHONE_ISDN -> "PHONE_ISDN"
                        AUDIO_VIDEO_UNCATEGORIZED -> "AUDIO_VIDEO_UNCATEGORIZED"
                        AUDIO_VIDEO_WEARABLE_HEADSET -> "AUDIO_VIDEO_WEARABLE_HEADSET"
                        AUDIO_VIDEO_HANDSFREE -> "AUDIO_VIDEO_HANDSFREE"
                        AUDIO_VIDEO_MICROPHONE -> "AUDIO_VIDEO_MICROPHONE"
                        AUDIO_VIDEO_LOUDSPEAKER -> "AUDIO_VIDEO_LOUDSPEAKER"
                        AUDIO_VIDEO_HEADPHONES -> "AUDIO_VIDEO_HEADPHONES"
                        AUDIO_VIDEO_PORTABLE_AUDIO -> "AUDIO_VIDEO_PORTABLE_AUDIO"
                        AUDIO_VIDEO_CAR_AUDIO -> "AUDIO_VIDEO_CAR_AUDIO"
                        AUDIO_VIDEO_SET_TOP_BOX -> "AUDIO_VIDEO_SET_TOP_BOX"
                        AUDIO_VIDEO_HIFI_AUDIO -> "AUDIO_VIDEO_HIFI_AUDIO"
                        AUDIO_VIDEO_VCR -> "AUDIO_VIDEO_VCR"
                        AUDIO_VIDEO_VIDEO_CAMERA -> "AUDIO_VIDEO_VIDEO_CAMERA"
                        AUDIO_VIDEO_CAMCORDER -> "AUDIO_VIDEO_CAMCORDER"
                        AUDIO_VIDEO_VIDEO_MONITOR -> "AUDIO_VIDEO_VIDEO_MONITOR"
                        AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER -> "AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER"
                        AUDIO_VIDEO_VIDEO_CONFERENCING -> "AUDIO_VIDEO_VIDEO_CONFERENCING"
                        AUDIO_VIDEO_VIDEO_GAMING_TOY -> "AUDIO_VIDEO_VIDEO_GAMING_TOY"
                        WEARABLE_UNCATEGORIZED -> "WEARABLE_UNCATEGORIZED"
                        WEARABLE_WRIST_WATCH -> "WEARABLE_WRIST_WATCH"
                        WEARABLE_PAGER -> "WEARABLE_PAGER"
                        WEARABLE_JACKET -> "WEARABLE_JACKET"
                        WEARABLE_HELMET -> "WEARABLE_HELMET"
                        WEARABLE_GLASSES -> "WEARABLE_GLASSES"
                        TOY_UNCATEGORIZED -> "TOY_UNCATEGORIZED"
                        TOY_ROBOT -> "TOY_ROBOT"
                        TOY_VEHICLE -> "TOY_VEHICLE"
                        TOY_DOLL_ACTION_FIGURE -> "TOY_DOLL_ACTION_FIGURE"
                        TOY_CONTROLLER -> "TOY_CONTROLLER"
                        TOY_GAME -> "TOY_GAME"
                        HEALTH_UNCATEGORIZED -> "HEALTH_UNCATEGORIZED"
                        HEALTH_BLOOD_PRESSURE -> "HEALTH_BLOOD_PRESSURE"
                        HEALTH_THERMOMETER -> "HEALTH_THERMOMETER"
                        HEALTH_WEIGHING -> "HEALTH_WEIGHING"
                        HEALTH_GLUCOSE -> "HEALTH_GLUCOSE"
                        HEALTH_PULSE_OXIMETER -> "HEALTH_PULSE_OXIMETER"
                        HEALTH_PULSE_RATE -> "HEALTH_PULSE_RATE"
                        HEALTH_DATA_DISPLAY -> "HEALTH_DATA_DISPLAY"
                        PERIPHERAL_NON_KEYBOARD_NON_POINTING -> "PERIPHERAL_NON_KEYBOARD_NON_POINTING"
                        PERIPHERAL_KEYBOARD -> "PERIPHERAL_KEYBOARD"
                        PERIPHERAL_POINTING -> "PERIPHERAL_POINTING"
                        PERIPHERAL_KEYBOARD_POINTING -> "PERIPHERAL_KEYBOARD_POINTING"
                        else -> "unknown"
                    }

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

                    Log.i(
                        TAG,
                        listOf(
                            "device=${result.device}(name=${result.device.name})",
                            "majorClass=$majorClass",
                            "class=$deviceClass",
                            "hasService=$hasService",
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
