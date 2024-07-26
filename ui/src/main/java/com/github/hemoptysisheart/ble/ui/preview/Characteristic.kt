package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.domain.Descriptor
import com.github.hemoptysisheart.ble.domain.Service
import com.github.hemoptysisheart.ble.spec.core.GATT_CHARACTERISTICS
import kotlin.random.Random

data class PreviewCharacteristic(
    override val type: com.github.hemoptysisheart.ble.spec.core.Characteristic,
    override val service: Service = PREVIEW_SERVICE_LIST.random(),
    override val readable: Boolean = true,
    override val writable: Boolean = Random.nextBoolean(),
    override val writableWithoutResponse: Boolean = Random.nextBoolean(),
    val indicatable: Boolean = Random.nextBoolean(),
    val notifiable: Boolean = Random.nextBoolean(),
    override val descriptors: List<Descriptor> = emptyList()
) : Characteristic

val PREVIEW_RANDOM_STANDARD_CHARACTERISTIC: PreviewCharacteristic
    get() = PreviewCharacteristic(
        type = GATT_CHARACTERISTICS.values.random(),
        descriptors = PREVIEW_STANDARD_DESCRIPTORS.map { PreviewDescriptor(it) }
    )

val PREVIEW_CHARACTERISTIC_LIST: List<Characteristic> = listOf(
    PREVIEW_RANDOM_STANDARD_CHARACTERISTIC,
    PreviewCharacteristic(type = GATT_CHARACTERISTICS.values.random())
)

class CharacteristicProvider : PreviewParameterProvider<Characteristic> {
    override val values: Sequence<Characteristic> = PREVIEW_CHARACTERISTIC_LIST.asSequence()
}

class CharacteristicListProvider : PreviewParameterProvider<List<Characteristic>> {
    override val values = sequenceOf(
        emptyList(),
        PREVIEW_CHARACTERISTIC_LIST
    )
}
