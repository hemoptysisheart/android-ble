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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_CONNECTION_LIST
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_RANDOM_DEVICE
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

    DetailPageContent(
        navigator = navigator,
        device = viewModel.device,
        onClickConnect = viewModel::onClickConnect,
        onClickDisconnect = viewModel::onClickDisconnect,
    )
}

@Composable
internal fun DetailPageContent(
    navigator: DetailNavigator,
    device: Device,
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

internal data class DetailPageParam(
    val device: Device
)

internal class DetailPageParamProvider : PreviewParameterProvider<DetailPageParam> {
    override val values: Sequence<DetailPageParam> = PREVIEW_CONNECTION_LIST.map {
        DetailPageParam(PREVIEW_RANDOM_DEVICE.copy(connection = it))
    }.asSequence()
}

@Composable
@PreviewPage
internal fun PreviewDetailPageContent(@PreviewParameter(DetailPageParamProvider::class) param: DetailPageParam) {
    AndroidBleTheme {
        DetailPageContent(
            navigator = DetailNavigator(baseNavigator()),
            device = param.device
        )
    }
}
