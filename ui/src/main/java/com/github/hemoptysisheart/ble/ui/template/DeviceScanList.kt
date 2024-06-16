package com.github.hemoptysisheart.ble.ui.template

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.organism.DeviceScanListItem
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_DEVICE_LIST

@Composable
fun DeviceScanList(
    devices: List<Device>,
    modifier: Modifier = Modifier,
    onClickDevice: (Device) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(devices) { device ->
            DeviceScanListItem(
                device = device,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onClick = onClickDevice
            )
        }
    }
}

internal data class DeviceScanListParam(
    val devices: List<Device>
)

internal class DeviceScanListParamProvider : PreviewParameterProvider<DeviceScanListParam> {
    override val values = sequenceOf(
        DeviceScanListParam(devices = emptyList()),
        DeviceScanListParam(devices = PREVIEW_DEVICE_LIST)
    )
}

@Composable
@PreviewLightDark
@PreviewFontScale
internal fun DeviceScanListPreview(
    @PreviewParameter(DeviceScanListParamProvider::class) param: DeviceScanListParam
) {
    AndroidBleTheme {
        DeviceScanList(devices = param.devices)
    }
}
