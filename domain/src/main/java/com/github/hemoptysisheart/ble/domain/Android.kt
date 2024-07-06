package com.github.hemoptysisheart.ble.domain

import com.github.hemoptysisheart.ble.spec.core.Characteristic
import com.github.hemoptysisheart.ble.spec.core.Service
import java.util.UUID

/**
 * Android 시스템이 제한하는 GATT의 UUID 목록.
 */
val RESTRICTED_UUID_LIST = listOf(
    "00001812-0000-1000-8000-00805F9B34FB", // Human Interface Device Service

    "00002A4A-0000-1000-8000-00805F9B34FB", // HID Information characteristic
    "00002A4B-0000-1000-8000-00805F9B34FB", // Report Map characteristic
    "00002A4C-0000-1000-8000-00805F9B34FB", // HID Control Point characteristic
    "00002A4D-0000-1000-8000-00805F9B34FB", // Report characteristic

    "00001843-0000-1000-8000-00805F9B34FB", // Audio Input Control Service (AICS)
    "00001844-0000-1000-8000-00805F9B34FB", // Volume Control Service (VCS)
    "00001845-0000-1000-8000-00805F9B34FB", // Volume Offset Control Service (VOCS)
    "00001846-0000-1000-8000-00805F9B34FB", // Coordinated Set Identification Service (CSIS)
    "0000184E-0000-1000-8000-00805F9B34FB", // Audio Stream Control Service (ASCS)
    "0000184F-0000-1000-8000-00805F9B34FB", // Broadcast Audio Scan Service (BASS)
    "00001850-0000-1000-8000-00805F9B34FB", // Published Audio Capabilities Service (PACS)
    "00001854-0000-1000-8000-00805F9B34FB", // Hearing Access Service

    "0000FFFD-0000-1000-8000-00805F9B34FB", // Universal Second Factor Authenticator Service (Fast IDentity Online Alliance (FIDO))

    "AB5E0001-5A21-4F05-BC7D-AF01F617B664" // Android TV Remote Service
).map { UUID.fromString(it) }

/**
 * Android 시스템이 사용을 제한하는 서비스이면 `true`, 그렇지 않으면 `false`.
 */
val Service.restricted: Boolean
    get() = RESTRICTED_UUID_LIST.contains(uuid)

/**
 * Android 시스템이 사용을 제한하는 특성이면 `true`, 그렇지 않으면 `false`.
 */
val Characteristic.restricted: Boolean
    get() = RESTRICTED_UUID_LIST.contains(uuid)
