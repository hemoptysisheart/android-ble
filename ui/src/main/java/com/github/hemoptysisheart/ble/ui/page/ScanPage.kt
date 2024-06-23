package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.ScanNavigator
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_DEVICE_LIST
import com.github.hemoptysisheart.ble.ui.template.DeviceScanList
import com.github.hemoptysisheart.ble.viewmodel.ScanViewModel
import com.github.hemoptysisheart.ui.compose.preview.PreviewPage
import com.github.hemoptysisheart.ui.navigation.compose.baseNavigator

@Composable
fun ScanPage(navigator: ScanNavigator, viewModel: ScanViewModel = hiltViewModel()) {
    Log.v(TAG, "#ScanPage args : navigator=$navigator")

    val scanning by viewModel.scanning.collectAsStateWithLifecycle()
    val devices by viewModel.devices.collectAsStateWithLifecycle()

    ScanPageContent(
        navigator = navigator,
        scanning = scanning,
        devices = devices,
        onClickScan = viewModel::onClickScan
    )
}

@Composable
internal fun ScanPageContent(
    navigator: ScanNavigator,
    scanning: Boolean,
    devices: List<Device>,
    onClickScan: () -> Unit = {}
) {
    Log.v(TAG, "#ScanPageContent args : navigator=$navigator, scanning=$scanning, devices=$devices")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DeviceScanList(
            devices = devices,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            onClickDevice = navigator::detail
        )

        HorizontalDivider(
            Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = onClickScan,
            modifier = Modifier.padding(16.dp),
            enabled = !scanning
        ) {
            Text(text = "검색", color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(8.dp))
        }
    }
}

internal data class ScanPageParam(
    val scanning: Boolean,
    val devices: List<Device>
)

internal class ScanPageParamProvider : PreviewParameterProvider<ScanPageParam> {
    override val values = sequenceOf(
        ScanPageParam(false, emptyList()),
        ScanPageParam(true, emptyList()),
        ScanPageParam(true, PREVIEW_DEVICE_LIST)
    )
}

@Composable
@PreviewPage
internal fun PreviewScanPageContent(@PreviewParameter(ScanPageParamProvider::class) param: ScanPageParam) {
    AndroidBleTheme {
        ScanPageContent(
            navigator = ScanNavigator(baseNavigator()),
            scanning = param.scanning,
            devices = param.devices
        )
    }
}
