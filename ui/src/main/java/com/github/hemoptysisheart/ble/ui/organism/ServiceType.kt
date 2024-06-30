package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.spec.core.GATT_SERVICES
import com.github.hemoptysisheart.ble.spec.core.Service
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun ColumnScope.ServiceType(service: Service, modifier: Modifier = Modifier) {
    Log.v(TAG, "#ServiceType args : service=$service, modifier=$modifier")

    HorizontalDivider(
        modifier = modifier
            .padding(start = 8.dp)
            .padding(vertical = 8.dp),
        color = MaterialTheme.colorScheme.primary
    )
    Row(modifier = modifier.padding(start = 8.dp)) {
        Text(
            text = "UUID : ",
            modifier = Modifier.padding(end = 8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = service.uuid.toString().uppercase(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Row(modifier = modifier.padding(start = 8.dp)) {
        Text(
            text = "ID : ",
            modifier = Modifier.padding(end = 8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = service.id ?: "N/A",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Row(modifier = modifier.padding(start = 8.dp)) {
        Text(
            text = "Name : ",
            modifier = Modifier.padding(end = 8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = service.name ?: "N/A",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
@PreviewComponent
internal fun PreviewServiceType() {
    AndroidBleTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            ServiceType(GATT_SERVICES.values.random())
        }
    }
}
