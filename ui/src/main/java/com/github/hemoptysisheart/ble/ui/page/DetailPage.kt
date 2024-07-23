package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator
import com.github.hemoptysisheart.ble.ui.preview.DeviceStateProvider
import com.github.hemoptysisheart.ble.ui.template.Device
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

    val device by viewModel.device.state.collectAsStateWithLifecycle()

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
    device: Device.State,
    onClickConnect: () -> Unit = { },
    onClickDisconnect: () -> Unit = { }
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

    Device(
        device = device,
        modifier = Modifier.fillMaxSize(),
        onClickConnect = onClickConnect,
        onClickDisconnect = onClickDisconnect
    )
}

@Composable
@PreviewPage
fun PreviewDetailPageContent(@PreviewParameter(DeviceStateProvider::class) device: Device.State) {
    AndroidBleTheme {
        DetailPageContent(
            navigator = DetailNavigator(baseNavigator()),
            device = device
        )
    }
}
