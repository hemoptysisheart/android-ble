package com.github.hemoptysisheart.ble.ui.template

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.ConnectionStateProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun ColumnScope.ConnectionTemplate(
    connection: Connection.State,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "#Connection args : connection=$connection")

    Text(
        text = "Connection Lv. : ${connection.level.label}",
        modifier = modifier.padding(start = 16.dp),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium
    )

    ServiceList(
        services = connection.services,
        modifier = modifier
            .weight(1F)
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 16.dp)
    )
}

@Composable
@PreviewComponent
fun ConnectionPreview(@PreviewParameter(ConnectionStateProvider::class) state: Connection.State) {
    AndroidBleTheme {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()) {
            ConnectionTemplate(state)
        }
    }
}
