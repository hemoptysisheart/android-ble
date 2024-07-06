package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.spec.core.GATT_CHARACTERISTICS
import kotlin.random.Random

data class PreviewCharacteristic(
    override val type: com.github.hemoptysisheart.ble.spec.core.Characteristic,
    override val readable: Boolean = true,
    override val writable: Boolean = Random.nextBoolean(),
    override val writableWithoutResponse: Boolean = Random.nextBoolean()
) : Characteristic

val PREVIEW_RANDOM_STANDARD_CHARACTERISTIC = PreviewCharacteristic(
    type = GATT_CHARACTERISTICS.values.random()
)

val PREVIEW_CHARACTERISTIC_LIST: List<Characteristic> = listOf(
    PREVIEW_RANDOM_STANDARD_CHARACTERISTIC
)

class CharacteristicProvider : PreviewParameterProvider<Characteristic> {
    override val values: Sequence<Characteristic> = PREVIEW_CHARACTERISTIC_LIST.asSequence()
}
