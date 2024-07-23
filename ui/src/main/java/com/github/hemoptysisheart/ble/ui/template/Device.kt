package com.github.hemoptysisheart.ble.ui.template

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.R
import com.github.hemoptysisheart.ble.ui.organism.Connection
import com.github.hemoptysisheart.ble.ui.organism.DeviceConnectionControl
import com.github.hemoptysisheart.ble.ui.organism.DeviceOverview
import com.github.hemoptysisheart.ble.ui.preview.DeviceStateProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun Device(
    device: Device.State, modifier: Modifier = Modifier,
    onClickConnect: () -> Unit = { },
    onClickDisconnect: () -> Unit = { }
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        DeviceOverview(
            device = device, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1F)
        ) {
            device.connection?.let { connection ->
                stickyHeader {
                    Text(
                        text = stringResource(R.string.domain_connection_label),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                item {
                    DeviceOverview(
                        device,
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp)
                    )
                }

                stickyHeader {
                    Text(
                        text = stringResource(R.string.domain_connection_prop_services_label),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                item {
                    Connection(connection)
                }
            }
        }
        HorizontalDivider(Modifier.fillMaxWidth())
        DeviceConnectionControl(
            level = device.connection?.level,
            onClickConnect = onClickConnect,
            onClickDisconnect = onClickDisconnect,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
@PreviewComponent
fun PreviewDevice(@PreviewParameter(DeviceStateProvider::class) device: Device.State) {
    Device(device = device)
}
