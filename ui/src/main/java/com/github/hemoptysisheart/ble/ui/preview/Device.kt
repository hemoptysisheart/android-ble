package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import com.github.hemoptysisheart.ble.spec.core.MajorServiceClass
import java.util.UUID
import kotlin.random.Random

data class PreviewDevice(
    override val name: String? = "PreviewDevice/${UUID.randomUUID()}",
    override val address: String = "00:00:00:00:00:00:00:00",
    override val category: DeviceClass = DeviceClass.entries.random(),
    override val services: List<MajorServiceClass> = MajorServiceClass.entries.filter { Random.nextBoolean() },
    override val rssi: Int = Random.nextInt(-100, 0),
    override var connection: Connection? = PREVIEW_CONNECTION_LIST.random()
) : Device {
    override fun connect(): Connection = throw UnsupportedOperationException("preview device does not support connect.")

    override fun disconnect() = throw UnsupportedOperationException("preview device does not support disconnect.")

    override fun compareTo(other: Device): Int = address.compareTo(other.address)
}

val PREVIEW_RANDOM_DEVICE = PreviewDevice(
    name = "Device A",
    address = "00:00:00:00:00:00:7B:A0"
)

val PREVIEW_DEVICE_INIT = PreviewDevice(
    name = "Device B",
    address = "00:00:00:00:00:01:7B:A1",
    connection = null
)

val PREVIEW_DEVICE_DISCONNECTED = PreviewDevice(
    name = null,
    address = "00:00:00:00:00:02:7B:A2",
    connection = PREVIEW_CONNECTION_DISCONNECTED
)

val PREVIEW_DEVICE_CONNECTING = PreviewDevice(
    name = "Device C",
    address = "00:00:00:00:00:03:7B:A3",
    connection = PREVIEW_CONNECTION_CONNECTING
)

val PREVIEW_DEVICE_CONNECTED = PreviewDevice(
    name = "Device D",
    address = "00:00:00:00:00:04:7B:A4",
    connection = PREVIEW_CONNECTION_CONNECTED
)

val PREVIEW_DEVICE_DISCONNECTING = PreviewDevice(
    name = "Device E",
    address = "00:00:00:00:00:05:7B:A5",
    connection = PREVIEW_CONNECTION_DISCONNECTING
)

val PREVIEW_DEVICE_LIST = listOf(
    PREVIEW_RANDOM_DEVICE,
    PREVIEW_DEVICE_INIT,
    PREVIEW_DEVICE_DISCONNECTED,
    PREVIEW_DEVICE_CONNECTING,
    PREVIEW_DEVICE_CONNECTED,
    PREVIEW_DEVICE_DISCONNECTING
)

internal class DevicePreviewProvider : PreviewParameterProvider<Device> {
    override val values = PREVIEW_DEVICE_LIST.asSequence()
}
