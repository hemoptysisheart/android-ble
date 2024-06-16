package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
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
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_DEVICE_LIST
import com.github.hemoptysisheart.ble.ui.state.ConnectionState
import com.github.hemoptysisheart.ble.ui.state.ConnectionState.CONNECTED
import com.github.hemoptysisheart.ble.ui.state.ConnectionState.CONNECTING
import com.github.hemoptysisheart.ble.ui.state.ConnectionState.DISCONNECTED
import com.github.hemoptysisheart.ble.ui.state.ConnectionState.DISCONNECTING
import com.github.hemoptysisheart.ble.ui.template.DeviceDetail
import com.github.hemoptysisheart.ble.viewmodel.DetailViewModel
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
        device = viewModel.device!!,
        connection = connection,
        onClickConnect = viewModel::onClickConnect,
        onClickDisconnect = viewModel::onClickDisconnect
    )
}

@Composable
internal fun DetailPageContent(
    navigator: DetailNavigator,
    device: Device,
    connection: ConnectionState,
    onClickConnect: () -> Unit = { },
    onClickDisconnect: () -> Unit = { }
) {
    Log.v(TAG, "#DetailPageContent args : navigator=$navigator, device=$device")

    Column(Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(32.dp))
        DeviceDetail(device, Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.weight(1F))

        HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = onClickConnect,
                modifier = Modifier.padding(8.dp),
                enabled = connection == DISCONNECTED
            ) {
                Text(text = "연결")
            }
            Button(
                onClick = onClickDisconnect,
                modifier = Modifier.padding(8.dp),
                enabled = connection == CONNECTED
            ) {
                Text(text = "해제")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

internal data class DetailPageParam(
    val device: Device,
    val connection: ConnectionState
)

internal class DetailPageParamProvider : PreviewParameterProvider<DetailPageParam> {
    override val values = sequenceOf(
        DetailPageParam(device = PREVIEW_DEVICE_LIST.random(), connection = DISCONNECTED),
        DetailPageParam(device = PREVIEW_DEVICE_LIST.random(), connection = CONNECTING),
        DetailPageParam(device = PREVIEW_DEVICE_LIST.random(), connection = CONNECTED),
        DetailPageParam(device = PREVIEW_DEVICE_LIST.random(), connection = DISCONNECTING),
    )
}

@Composable
@PreviewLightDark
@PreviewFontScale
internal fun Preview_DetailPage(@PreviewParameter(DetailPageParamProvider::class) param: DetailPageParam) {
    AndroidBleTheme {
        DetailPageContent(
            navigator = DetailNavigator(baseNavigator()),
            device = param.device,
            connection = param.connection
        )
    }
}
