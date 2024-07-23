package com.github.hemoptysisheart.ble.ui.organism

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.ui.preview.ConnectionLevelProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun DeviceConnectionControl(
    level: Connection.Level?,
    modifier: Modifier = Modifier,
    onClickConnect: () -> Unit = { },
    onClickDisconnect: () -> Unit = { }
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = onClickConnect, enabled = null == level || Connection.Level.DISCONNECTED == level) {
            Text(
                text = "Connect",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = onClickDisconnect, enabled = Connection.Level.CONNECTED == level) {
            Text(
                text = "Disconnect",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
@PreviewComponent
fun PreviewDeviceConnectionControl(@PreviewParameter(ConnectionLevelProvider::class) level: Connection.Level) {
    DeviceConnectionControl(
        level = level,
        modifier = Modifier.padding(8.dp)
    )
}