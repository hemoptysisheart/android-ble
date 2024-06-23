package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.hemoptysisheart.ble.spec.core.Service
import com.github.hemoptysisheart.ble.spec.core.ServiceImpl
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent
import java.util.UUID

@Composable
fun ServiceType(service: Service, modifier: Modifier = Modifier) {
    Log.v(TAG, "#ServiceType args : service=$service, modifier=$modifier")

    Text(text = "UUID : ${service.uuid.toString().uppercase()}", modifier = modifier)
}

@Composable
@PreviewComponent
internal fun PreviewServiceType() {
    AndroidBleTheme {
        ServiceType(ServiceImpl(UUID.fromString("00001800-0000-1000-8000-00805f9b34fb")))
    }
}
