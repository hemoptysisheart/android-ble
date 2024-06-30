package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_CONNECTION_LIST
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_CONNECTION_STATE_LIST
import com.github.hemoptysisheart.ble.ui.template.ConnectionTemplate
import com.github.hemoptysisheart.ble.ui.template.DeviceDetail
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

    val connection by viewModel.connection.collectAsStateWithLifecycle()

    DetailPageContent(
        navigator = navigator,
        device = viewModel.device,
        connection = connection,
        onClickConnect = viewModel::onClickConnect,
        onClickDisconnect = viewModel::onClickDisconnect
    )
}

@Composable
internal fun DetailPageContent(
    navigator: DetailNavigator,
    device: Device,
    connection: Connection.State,
    onClickConnect: () -> Unit = { },
    onClickDisconnect: () -> Unit = { }
) {
    Log.v(
        TAG,
        listOf(
            "navigator=$navigator",
            "device=$device",
            "connection=$connection",
            "onClickConnect=$onClickConnect",
            "onClickDisconnect=$onClickDisconnect"
        ).joinToString(", ", "#DetailPageContent args : ")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        DeviceDetail(device, Modifier.fillMaxWidth())
        ConnectionTemplate(connection, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.weight(1F))
        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.outlineVariant)

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = onClickConnect,
                modifier = Modifier.padding(8.dp),
                enabled = Level.DISCONNECTED == connection.level
            ) {
                Text(text = "연결", color = MaterialTheme.colorScheme.onPrimary)
            }
            Button(
                onClick = onClickDisconnect,
                modifier = Modifier.padding(8.dp),
                enabled = Level.CONNECTED == connection.level
            ) {
                Text(text = "해제", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

internal data class DetailPageParam(
    val device: Device,
    val connection: Connection.State
)

internal class DetailPageParamProvider : PreviewParameterProvider<DetailPageParam> {
    override val values = PREVIEW_CONNECTION_STATE_LIST.map {
        DetailPageParam(
            device = PREVIEW_CONNECTION_LIST.random().device,
            connection = it
        )
    }.asSequence()
}

@Composable
@PreviewPage
internal fun PreviewDetailPageContent(@PreviewParameter(DetailPageParamProvider::class) param: DetailPageParam) {
    AndroidBleTheme {
        DetailPageContent(
            navigator = DetailNavigator(baseNavigator()),
            device = param.device,
            connection = param.connection
        )
    }
}
