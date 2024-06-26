package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_DEVICE_LIST
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun LazyItemScope.DeviceScanListItem(
    device: Device,
    modifier: Modifier = Modifier,
    onClick: (Device) -> Unit = { }
) {
    Log.v(TAG, "#DeviceScanCard args : device=$device, modifier=$modifier, onClick=$onClick")

    ListItem(
        headlineContent = {
            device.name.let { name ->
                if (null == name) {
                    Text(
                        text = "null",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium
                    )
                } else {
                    Text(
                        text = name,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        modifier = modifier,
        supportingContent = {
            Column {
                Text(
                    text = "Category : ${device.category.label}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                device.services.let { services ->
                    if (services.isNotEmpty()) {
                        Text(
                            text = services.joinToString(", ", "Service : ") { it.label },
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Text(
                    text = "Address : ${device.address}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "RSSI : ${device.rssi} dBm",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        trailingContent = {
            OutlinedButton(onClick = { onClick(device) }) {
                Text(text = "상세")
            }
        }
    )
}

@Composable
@PreviewComponent
internal fun DeviceScanCardPreview() {
    AndroidBleTheme {
        LazyColumn(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            item {
                DeviceScanListItem(PREVIEW_DEVICE_LIST.random())
            }
        }
    }
}
