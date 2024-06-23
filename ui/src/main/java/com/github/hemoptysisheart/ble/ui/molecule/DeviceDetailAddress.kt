package com.github.hemoptysisheart.ble.ui.molecule

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.DevicePreviewProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun DeviceDetailAddress(device: Device, modifier: Modifier = Modifier) {
    Log.v(TAG, "#DeviceDetailAddress args : device=$device, modifier=$modifier")

    Text(
        text = "Address : ${device.address}",
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
@PreviewComponent
internal fun DeviceDetailAddressPreview(@PreviewParameter(DevicePreviewProvider::class) device: Device) {
    AndroidBleTheme {
        DeviceDetailAddress(device)
    }
}
