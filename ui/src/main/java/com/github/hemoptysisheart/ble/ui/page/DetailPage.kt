package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator
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

    Text(text = "$device")
}

internal data class DetailPageParam(
    val device: Device
)

internal class DetailPageParamProvider : PreviewParameterProvider<DetailPageParam> {
    override val values: Sequence<DetailPageParam> = sequenceOf()
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
