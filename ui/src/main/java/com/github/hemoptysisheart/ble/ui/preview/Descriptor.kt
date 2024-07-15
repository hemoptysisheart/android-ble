package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.Descriptor
import com.github.hemoptysisheart.ble.spec.core.GATT_DESCRIPTORS
import kotlin.random.Random

class PreviewDescriptor(
    override val type: com.github.hemoptysisheart.ble.spec.core.Descriptor,
    val readable: Boolean = Random.nextBoolean(),
    val writable: Boolean = Random.nextBoolean()
) : Descriptor

val PREVIEW_DESCRIPTORS: List<Descriptor> = setOf(
    PreviewDescriptor(GATT_DESCRIPTORS.values.random()),
    PreviewDescriptor(GATT_DESCRIPTORS.values.random()),
    PreviewDescriptor(GATT_DESCRIPTORS.values.random()),
    PreviewDescriptor(GATT_DESCRIPTORS.values.random()),
).toList()

class DescriptorProvider : PreviewParameterProvider<Descriptor> {
    override val values = PREVIEW_DESCRIPTORS.asSequence()
}
