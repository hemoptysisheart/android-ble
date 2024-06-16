package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_DEVICE_LIST

@Composable
fun DeviceScanCard(
    device: Device,
    modifier: Modifier = Modifier,
    onClick: (Device) -> Unit = { }
) {
    Log.v(TAG, "#DeviceScanCard args : device=$device, modifier=$modifier, onClick=$onClick")

    OutlinedCard(modifier = modifier.clickable { onClick(device) }) {
        Text(
            text = "name : ${device.name ?: "null"}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 2.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "address : ${device.address}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 2.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "category : ${device.category.label}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 2.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "rssi : ${device.rssi} dBm",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 2.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
@PreviewLightDark
@PreviewFontScale
internal fun DeviceScanCardPreview() {
    AndroidBleTheme {
        DeviceScanCard(PREVIEW_DEVICE_LIST.random())
    }
}
