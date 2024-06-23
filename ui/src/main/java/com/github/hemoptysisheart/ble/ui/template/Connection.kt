package com.github.hemoptysisheart.ble.ui.template

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.ConnectionStateProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun ColumnScope.Connection(
    connection: Connection.State,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "#Connection args : connection=$connection")

    Text(
        text = "Level : ${connection.level}",
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium
    )
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
