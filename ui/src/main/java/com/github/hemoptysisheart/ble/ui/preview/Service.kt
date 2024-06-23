package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.Service
import com.github.hemoptysisheart.ble.spec.core.ServiceImpl
import java.util.UUID


class PreviewService(
    override val type: com.github.hemoptysisheart.ble.spec.core.Service
) : Service {
    override fun toString() = listOf(
        "type=$type"
    ).joinToString(", ", "PreviewService(", ")")
}

val PREVIEW_SERVICE_GAP = PreviewService(
    type = ServiceImpl(
        uuid = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb"),
        characteristics = emptyList()
    )
)

val PREVIEW_SERVICE_LIST: List<Service> = listOf(
    PREVIEW_SERVICE_GAP
)

class ServiceProvider : PreviewParameterProvider<Service> {
    override val values: Sequence<Service> = PREVIEW_SERVICE_LIST.asSequence()
}
