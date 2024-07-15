package com.github.hemoptysisheart.ble.ui.template

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.domain.Service
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.organism.Service
import com.github.hemoptysisheart.ble.ui.preview.ServiceListProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun ServiceList(
    services: List<Service>?,
    modifier: Modifier = Modifier,
    onClickNotification: (Characteristic) -> Unit = {},
    onClickIndication: (Characteristic) -> Unit = {},
    onClickRead: (Characteristic) -> Unit = {}
) {
    if (null == services) {
        ServiceListNull(modifier = modifier)
    } else {
        ServiceListNotNull(
            services = services,
            modifier = modifier,
            onClickNotification = onClickNotification,
            onClickIndication = onClickIndication,
            onClickRead = onClickRead
        )
    }
}

@Composable
fun ServiceListNull(modifier: Modifier = Modifier) {
    Log.v(TAG, "#ServiceListNull args : modifier=$modifier")

    Text(text = "서비스 목록 정보가 없습니다.", modifier = modifier, color = MaterialTheme.colorScheme.onSurface)
}

@Composable
fun ServiceListNotNull(
    services: List<Service>,
    modifier: Modifier = Modifier,
    onClickNotification: (Characteristic) -> Unit = {},
    onClickIndication: (Characteristic) -> Unit = {},
    onClickRead: (Characteristic) -> Unit = {}
) {
    Log.v(TAG, "#ServiceListNotNull args : services=$services, modifier=$modifier, onClickRead=$onClickRead")

    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = "서비스 목록 :",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleSmall
            )
        }

        items(services) { service ->
            Service(
                service = service,
                modifier = Modifier.fillMaxWidth(),
                onClickNotification = onClickNotification,
                onClickIndication = onClickIndication,
                onClickRead = onClickRead
            )
        }
    }
}

@Composable
@PreviewComponent
fun PreviewServiceList(
    @PreviewParameter(ServiceListProvider::class) services: List<Service>?
) {
    AndroidBleTheme {
        ServiceList(
            services = services,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        )
    }
}
