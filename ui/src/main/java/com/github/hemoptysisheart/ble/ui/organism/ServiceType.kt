package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.spec.core.CharacteristicImpl
import com.github.hemoptysisheart.ble.spec.core.Service
import com.github.hemoptysisheart.ble.spec.core.ServiceImpl
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent
import java.util.UUID

@Composable
fun ColumnScope.ServiceType(service: Service, modifier: Modifier = Modifier) {
    Log.v(TAG, "#ServiceType args : service=$service, modifier=$modifier")

    HorizontalDivider(
        modifier = modifier
            .padding(start = 8.dp)
            .padding(vertical = 8.dp),
        color = MaterialTheme.colorScheme.primary
    )
    Row(modifier = modifier.padding(start = 8.dp)) {
        Text(
            text = "UUID : ",
            modifier = Modifier.padding(end = 8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = service.uuid.toString().uppercase(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Text(
        text = "Characteristic :",
        modifier = modifier.padding(start = 8.dp),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium
    )
    for (characteristic in service.characteristics) {
        Row(
            modifier = modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "UUID : ",
                modifier = Modifier.padding(end = 8.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = characteristic.uuid.toString().uppercase(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        characteristic.descriptors.let { descriptors ->
            if (descriptors.isNotEmpty()) {
                HorizontalDivider(modifier = modifier
                    .padding(start = 16.dp)
                    .padding(vertical = 4.dp))
                Text(
                    text = "Descriptor :",
                    modifier = modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium
                )
                for (descriptor in descriptors) {
                    Row(
                        modifier = modifier.padding(start = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "UUID : ",
                            modifier = Modifier.padding(end = 8.dp),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = descriptor.uuid.toString().uppercase(),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
@PreviewComponent
internal fun PreviewServiceType() {
    AndroidBleTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            ServiceType(
                ServiceImpl(
                    uuid = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb"),
                    characteristics = listOf(
                        CharacteristicImpl(UUID.randomUUID(), emptyList())
                    )
                )
            )
        }
    }
}
