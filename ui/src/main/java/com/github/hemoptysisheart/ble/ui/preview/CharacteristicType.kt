package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.spec.core.Characteristic
import com.github.hemoptysisheart.ble.spec.core.CustomCharacteristic
import com.github.hemoptysisheart.ble.spec.core.GATT_CHARACTERISTICS
import java.util.UUID

val PREVIEW_CHARACTERISTIC_TYPE_LIST = listOf(
    CustomCharacteristic(UUID.randomUUID()),
    GATT_CHARACTERISTICS.values.random(),
    GATT_CHARACTERISTICS.values.random(),
    GATT_CHARACTERISTICS.values.random()
)

class CharacteristicTypeProvider : PreviewParameterProvider<Characteristic> {
    override val values: Sequence<Characteristic> = sequenceOf(
        CustomCharacteristic(UUID.randomUUID()),
        GATT_CHARACTERISTICS.values.random(),
        GATT_CHARACTERISTICS.values.random(),
        GATT_CHARACTERISTICS.values.random()
    )
}

class CharacteristicTypeListProvider : PreviewParameterProvider<List<Characteristic>> {
    override val values = sequenceOf(
        emptyList(),
        PREVIEW_CHARACTERISTIC_TYPE_LIST
    )
}
