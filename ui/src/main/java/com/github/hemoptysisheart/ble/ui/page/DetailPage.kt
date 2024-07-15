package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator
import com.github.hemoptysisheart.ble.ui.preview.DeviceStateProvider
import com.github.hemoptysisheart.ble.ui.state.DeviceState
import com.github.hemoptysisheart.ble.viewmodel.DetailViewModel
import com.github.hemoptysisheart.ui.compose.preview.PreviewPage
import com.github.hemoptysisheart.ui.navigation.compose.baseNavigator
import com.github.hemoptysisheart.ui.navigation.compose.baseViewModel

@Composable
fun DetailPage(
    navigator: DetailNavigator,
    viewModel: DetailViewModel = baseViewModel()
) {
    Log.v(TAG, "#DetailPage args : navigator=$navigator, viewModel=$viewModel")

    val device by viewModel.state.collectAsStateWithLifecycle()

    DetailPageContent(
        navigator = navigator,
        device = device,
        onClickConnect = viewModel::onClickConnect,
        onClickDisconnect = viewModel::onClickDisconnect,
    )
}

@Composable
internal fun DetailPageContent(
    navigator: DetailNavigator,
    device: DeviceState,
    onClickConnect: () -> Unit = { },
    onClickDisconnect: () -> Unit = { },
) {
    Log.v(
        TAG,
        listOf(
            "navigator=$navigator",
            "device=$device",
            "onClickConnect=$onClickConnect",
            "onClickDisconnect=$onClickDisconnect",
        ).joinToString(", ", "#DetailPageContent args : ")
    )

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$device")
        Spacer(modifier = Modifier.weight(1F))
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = onClickConnect,
                Modifier.padding(8.dp),
                enabled = device.connection == null || Connection.Level.DISCONNECTED == device.connection?.level
            ) {
                Text(
                    text = "Connect",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = onClickConnect,
                Modifier.padding(8.dp),
                enabled = Connection.Level.CONNECTED == device.connection?.level
            ) {
                Text(
                    text = "Disconnect",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
@PreviewPage
internal fun PreviewDetailPageContent(@PreviewParameter(DeviceStateProvider::class) device: DeviceState) {
    AndroidBleTheme {
        DetailPageContent(
            navigator = DetailNavigator(baseNavigator()),
            device = device
        )
    }
}
