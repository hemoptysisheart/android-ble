package com.github.hemoptysisheart.ble.ui.molecule

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.DevicePreviewProvider

@Composable
fun DeviceDetailRssi(device: Device, modifier: Modifier = Modifier) {
    Log.v(TAG, "#DeviceDetailRssi args : device=$device, modifier=$modifier")

    Text(
        text = "${device.rssi} dBm",
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
@PreviewFontScale
@PreviewLightDark
internal fun DeviceDetailRssiPreview(@PreviewParameter(DevicePreviewProvider::class) device: Device) {
    AndroidBleTheme {
        DeviceDetailRssi(device)
    }
}