package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.domain.Service
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.molecule.ServiceType
import com.github.hemoptysisheart.ble.ui.preview.ServiceProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun LazyItemScope.Service(
    service: Service,
    modifier: Modifier = Modifier,
    onClickNotification: (Characteristic) -> Unit = {},
    onClickRead: (Characteristic) -> Unit = {}
) {
    Log.v(TAG, "#Service args : service=$service, modifier=$modifier, onClickRead=$onClickRead")

    ListItem(
        headlineContent = {
            ServiceType(service = service.type, modifier = Modifier.fillMaxWidth())
        },
        supportingContent = {
            CharacteristicList(
                characteristics = service.characteristics,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(),
                onClickNotification = onClickNotification,
                onClickRead = onClickRead
            )
        },
        modifier = modifier
    )
}

@Composable
@PreviewComponent
fun PreviewService(@PreviewParameter(ServiceProvider::class) service: Service) {
    AndroidBleTheme {
        LazyColumn {
            item {
                Service(
                    service = service,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}
