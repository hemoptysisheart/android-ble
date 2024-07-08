package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.spec.core.CustomDescriptor
import com.github.hemoptysisheart.ble.spec.core.Descriptor
import com.github.hemoptysisheart.ble.spec.core.GATT_DESCRIPTORS
import java.util.UUID

val PREVIEW_STANDARD_DESCRIPTORS: List<Descriptor> = setOf(
    GATT_DESCRIPTORS.values.random(),
    GATT_DESCRIPTORS.values.random(),
    GATT_DESCRIPTORS.values.random(),
    GATT_DESCRIPTORS.values.random()
).toList()

class DescriptorTypeProvider : PreviewParameterProvider<Descriptor> {
    override val values = (
            PREVIEW_STANDARD_DESCRIPTORS + setOf(
                CustomDescriptor(UUID.randomUUID())
            )).asSequence()
}

class DescriptorTypeListProvider : PreviewParameterProvider<List<Descriptor>> {
    override val values = sequenceOf(
        emptyList(),
        listOf(GATT_DESCRIPTORS.values.random()),
        PREVIEW_STANDARD_DESCRIPTORS
    )
}
