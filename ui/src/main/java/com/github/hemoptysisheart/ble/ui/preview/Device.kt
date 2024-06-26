package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.AbstractDevice
import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import com.github.hemoptysisheart.ble.spec.core.MajorServiceClass

val PREVIEW_DEVICE_1 = object : AbstractDevice() {
    override val name = "Device A"
    override val address = "00:00:00:00:00:00:7B:AE"
    override val category: DeviceClass = DeviceClass.entries.random()
    override val services: List<MajorServiceClass> = emptyList()
    override val rssi = -60
}

val PREVIEW_DEVICE_2 = object : AbstractDevice() {
    override val name = "Device B"
    override val address = "00:00:00:00:00:01:7B:AE"
    override val category: DeviceClass = DeviceClass.entries.random()
    override val services: List<MajorServiceClass> = listOf(MajorServiceClass.entries.random())
    override val rssi = -94
}

val PREVIEW_DEVICE_3 = object : AbstractDevice() {
    override val name = null
    override val address = "00:00:00:00:00:02:7B:AE"
    override val category: DeviceClass = DeviceClass.entries.random()
    override val services: List<MajorServiceClass> = MajorServiceClass.entries.toList()
    override val rssi = -99
}

val PREVIEW_DEVICE_LIST = listOf(PREVIEW_DEVICE_1, PREVIEW_DEVICE_2, PREVIEW_DEVICE_3)

internal class DevicePreviewProvider : PreviewParameterProvider<AbstractDevice> {
    override val values = PREVIEW_DEVICE_LIST.asSequence()
}
