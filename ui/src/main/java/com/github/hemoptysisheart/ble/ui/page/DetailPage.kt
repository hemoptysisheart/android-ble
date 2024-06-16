package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_DEVICE_1
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_DEVICE_2
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_DEVICE_3
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

    DetailPageContent(navigator, viewModel.device!!)
}

@Composable
internal fun DetailPageContent(
    navigator: DetailNavigator,
    device: Device
) {
    Log.v(TAG, "#DetailPageContent args : navigator=$navigator, device=$device")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
    ) {
        DeviceDetail(device, Modifier.fillMaxWidth())
    }
}

internal data class DetailPageParam(
    val device: Device
)

internal class DetailPageParamProvider : PreviewParameterProvider<DetailPageParam> {
    override val values = sequenceOf(
        DetailPageParam(device = PREVIEW_DEVICE_1),
        DetailPageParam(device = PREVIEW_DEVICE_2),
        DetailPageParam(device = PREVIEW_DEVICE_3)
    )
}

@Composable
@PreviewLightDark
@PreviewFontScale
internal fun Preview_DetailPage(@PreviewParameter(DetailPageParamProvider::class) param: DetailPageParam) {
    AndroidBleTheme {
        DetailPageContent(
            navigator = DetailNavigator(baseNavigator()),
            device = param.device
        )
    }
}
