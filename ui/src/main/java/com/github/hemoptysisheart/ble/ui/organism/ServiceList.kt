package com.github.hemoptysisheart.ble.ui.organism

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Service
import com.github.hemoptysisheart.ble.ui.preview.PREVIEW_SERVICE_LIST
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun ServiceList(services: List<Service>) {
    services.forEach { service ->
        Column(
            modifier = Modifier
                .padding(8.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            ServiceOverview(service, Modifier.fillMaxWidth())

            CharacteristicList(characteristics = service.characteristics, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
@PreviewComponent
internal fun PreviewServiceList() {
    ServiceList(PREVIEW_SERVICE_LIST)
}