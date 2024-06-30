package com.github.hemoptysisheart.ble.ui.molecule

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.spec.core.Service
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.ServiceTypeProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

/**
 * [Service]용 UI.
 */
@Composable
fun ServiceType(service: Service, modifier: Modifier = Modifier) {
    Log.v(TAG, "#ServiceType args : service=$service, modifier=$modifier")

    Column(modifier = modifier) {
        service.name.let { name ->
            if (null == name) {
                Text(
                    text = "N/A",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleSmall
                )
            } else {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        Text(
            text = "UUID : ${service.uuid}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
@PreviewComponent
fun PreviewServiceType(@PreviewParameter(ServiceTypeProvider::class) service: Service) {
    AndroidBleTheme {
        ServiceType(
            service = service,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        )
    }
}
