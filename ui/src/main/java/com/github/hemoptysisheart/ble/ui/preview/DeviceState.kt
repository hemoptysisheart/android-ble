package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.ui.state.DeviceState


internal class DeviceStateProvider : PreviewParameterProvider<DeviceState> {
    override val values = PREVIEW_DEVICE_LIST.map {
        DeviceState(
            name = it.name,
            address = it.address,
            category = it.category,
            services = it.services,
            rssi = it.rssi,
            connection = it.connection?.state?.value
        )
    }.asSequence()
}