package com.github.hemoptysisheart.ble.ui.molecule

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.spec.core.MajorServiceClass
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.MajorServiceClassListProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun ColumnScope.DeviceDetailServices(services: Collection<MajorServiceClass>, modifier: Modifier = Modifier) {
    Log.v(TAG, "#DeviceDetailServices args : services=$services, modifier=$modifier")

    if (services.isNotEmpty()) {
        Text(
            text = "Service : ${
                MajorServiceClass.entries.filter { services.contains(it) }.joinToString(", ") { it.label }
            }",
            modifier = modifier,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
@PreviewComponent
internal fun DeviceDetailServicesPreview(@PreviewParameter(MajorServiceClassListProvider::class) services: List<MajorServiceClass>) {
    AndroidBleTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            DeviceDetailServices(services)
        }
    }
}
