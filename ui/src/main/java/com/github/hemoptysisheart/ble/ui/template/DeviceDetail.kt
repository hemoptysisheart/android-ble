package com.github.hemoptysisheart.ble.ui.template

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.molecule.DeviceDetailAddress
import com.github.hemoptysisheart.ble.ui.molecule.DeviceDetailCategory
import com.github.hemoptysisheart.ble.ui.molecule.DeviceDetailName
import com.github.hemoptysisheart.ble.ui.molecule.DeviceDetailRssi
import com.github.hemoptysisheart.ble.ui.preview.DevicePreviewProvider

@Composable
fun ColumnScope.DeviceDetail(device: Device, modifier: Modifier = Modifier) {
    Log.v(TAG, "#DeviceDetail args : device=$device")

    Column(modifier = modifier) {
        DeviceDetailName(device,
            Modifier
                .fillMaxWidth()
                .padding(8.dp))
        DeviceDetailCategory(device,
            Modifier
                .fillMaxWidth()
                .padding(4.dp))
        DeviceDetailAddress(device,
            Modifier
                .fillMaxWidth()
                .padding(4.dp))
        DeviceDetailRssi(device,
            Modifier
                .fillMaxWidth()
                .padding(4.dp))
    }
}

@Composable
@PreviewLightDark
@PreviewFontScale
internal fun DeviceCardPreview(
    @PreviewParameter(DevicePreviewProvider::class) device: Device
) {
    AndroidBleTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            DeviceDetail(device)
        }
    }
}
