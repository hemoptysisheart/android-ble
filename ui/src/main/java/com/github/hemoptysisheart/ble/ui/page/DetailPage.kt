package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.AbstractDevice
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$device")
    }
}

internal data class DetailPageParam(
    val device: Device
)

internal class DetailPageParamProvider : PreviewParameterProvider<DetailPageParam> {
    override val values = sequenceOf(
        DetailPageParam(
            device = object : AbstractDevice() {
                override val address = "00:00:00:00:00:00"
                override val category = DeviceClass.entries.random()
                override val rssi: Int = -100
                override val name = "device"
            }
        )
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
