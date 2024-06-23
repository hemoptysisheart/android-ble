package com.github.hemoptysisheart.ble.ui.template

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Service
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.organism.ServiceType
import com.github.hemoptysisheart.ble.ui.preview.ServiceProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

/**
 * [Service] 표시 컴포넌트.
 */
@Composable
fun ColumnScope.Service(service: Service, modifier: Modifier = Modifier) {
    Log.v(TAG, "#Service args : service=$service, modifier=$modifier")

    ServiceType(service.type, modifier)
}

@Composable
@PreviewComponent
internal fun ServicePreview(@PreviewParameter(ServiceProvider::class) service: Service) {
    AndroidBleTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
            Service(service)
        }
    }
}
