package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.ui.preview.ConnectionStateProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun Connection(connection: Connection.State) {
    Log.v(TAG, "#Connection args : connection=$connection")

    Column(Modifier.fillMaxWidth()) {
        ConnectionOverview(connection = connection, modifier = Modifier.padding(8.dp))
        ServiceList(services = connection.services)
    }
}

@Composable
@PreviewComponent
fun PreviewConnection(@PreviewParameter(ConnectionStateProvider::class) connection: Connection.State) {
    Connection(connection)
}
