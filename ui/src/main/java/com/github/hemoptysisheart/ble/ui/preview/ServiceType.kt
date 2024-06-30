package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.spec.core.CustomService
import com.github.hemoptysisheart.ble.spec.core.GATT_SERVICES
import com.github.hemoptysisheart.ble.spec.core.Service
import java.util.UUID

class ServiceTypeProvider : PreviewParameterProvider<Service> {
    override val values: Sequence<Service> = sequenceOf(
        CustomService(UUID.randomUUID()),
        GATT_SERVICES.values.random(),
        GATT_SERVICES.values.random()
    )
}
