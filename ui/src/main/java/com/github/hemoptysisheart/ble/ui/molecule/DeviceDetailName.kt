package com.github.hemoptysisheart.ble.ui.molecule

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.DevicePreviewProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun ColumnScope.DeviceDetailName(device: Device, modifier: Modifier = Modifier) {
    Log.v(TAG, "#DeviceDetailName args : device=$device, modifier=$modifier")

    device.name.let { name ->
        if (null == name) {
            Text(
                text = "Name : null",
                modifier = modifier,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            Text(
                text = "Name : $name",
                modifier = modifier,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
@PreviewComponent
internal fun DeviceDetailNamePreview(
    @PreviewParameter(DevicePreviewProvider::class) device: Device
) {
    AndroidBleTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            DeviceDetailName(device)
        }
    }
}
