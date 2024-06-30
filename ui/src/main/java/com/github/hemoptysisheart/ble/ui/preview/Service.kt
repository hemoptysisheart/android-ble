package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.Service
import com.github.hemoptysisheart.ble.spec.core.GATT_SERVICES


class PreviewService(
    override val type: com.github.hemoptysisheart.ble.spec.core.Service
) : Service {
    override fun toString() = listOf(
        "type=$type"
    ).joinToString(", ", "PreviewService(", ")")
}

val PREVIEW_RANDOM_STANDARD_SERVICE = PreviewService(
    type = GATT_SERVICES.values.random()
)

val PREVIEW_SERVICE_LIST: List<Service> = listOf(
    PREVIEW_RANDOM_STANDARD_SERVICE
)

class ServiceProvider : PreviewParameterProvider<Service> {
    override val values: Sequence<Service> = PREVIEW_SERVICE_LIST.asSequence()
}
