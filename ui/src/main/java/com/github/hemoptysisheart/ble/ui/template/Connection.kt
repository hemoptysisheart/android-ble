package com.github.hemoptysisheart.ble.ui.template

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.organism.ServiceType
import com.github.hemoptysisheart.ble.ui.preview.ConnectionStateProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun ColumnScope.Connection(
    connection: Connection.State,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "#Connection args : connection=$connection")

    Text(
        text = "Level : ${connection.level.label}",
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium
    )

    connection.services?.let { services ->
        Text(
            text = "서비스 목록",
            modifier = modifier,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )

        for (service in services) {
            ServiceType(service = service.type, modifier = modifier.padding(start = 16.dp))

            Text(
                text = "캐릭터리스틱 목록",
                modifier = modifier.padding(start = 24.dp),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )

            for (characteristic in service.characteristics) {
                Text(
                    text = characteristic.type.let {
                        "UUID : ${it.uuid}\nID : ${it.id ?: "N/A"}\nName : ${it.name ?: "N/A"}"
                    },
                    modifier = modifier.padding(start = 32.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
@PreviewComponent
fun ConnectionPreview(@PreviewParameter(ConnectionStateProvider::class) state: Connection.State) {
    AndroidBleTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            Connection(state)
        }
    }
}
