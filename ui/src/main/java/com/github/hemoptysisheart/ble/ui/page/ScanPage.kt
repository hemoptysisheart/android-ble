package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.ble.domain.AbstractDevice
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.ScanNavigator
import com.github.hemoptysisheart.ble.viewmodel.ScanViewModel
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
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            items(devices) { device ->
                OutlinedCard(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { navigator.detail(device) }
                ) {
                    Text(
                        text = "name : ${device.name ?: "null"}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 2.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "address : ${device.address}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 2.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "category : ${device.category.label}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 2.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "rssi : ${device.rssi} dBm",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 2.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        HorizontalDivider(
            Modifier
                .fillMaxWidth()
        )
        Button(
            onClick = onClickScan,
            modifier = Modifier.padding(16.dp),
            enabled = !scanning
        ) {
            Text(text = "검색", modifier = Modifier.padding(8.dp))
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
        ScanPageParam(
            scanning = true,
            devices = listOf(
                object : AbstractDevice() {
                    override val name = "Device A"
                    override val address = "00:00:00:00:00:00:7B:AE"
                    override val category: DeviceClass = DeviceClass.entries.random()
                    override val rssi = -60
                },
                object : AbstractDevice() {
                    override val name = "Device B"
                    override val address = "00:00:00:00:00:01:7B:AE"
                    override val category: DeviceClass = DeviceClass.entries.random()
                    override val rssi = -94
                },
                object : AbstractDevice() {
                    override val name = null
                    override val address = "00:00:00:00:00:02:7B:AE"
                    override val category: DeviceClass = DeviceClass.entries.random()
                    override val rssi = -99
                }
            )
        )
    )
}

@Composable
@PreviewLightDark
@PreviewFontScale
internal fun ScanPagePreview(@PreviewParameter(ScanPageParamProvider::class) param: ScanPageParam) {
    AndroidBleTheme {
        ScanPageContent(
            navigator = ScanNavigator(baseNavigator()),
            scanning = param.scanning,
            devices = param.devices
        )
    }
}
