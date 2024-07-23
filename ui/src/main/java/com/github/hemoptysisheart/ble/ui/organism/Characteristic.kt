package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.ui.preview.CharacteristicProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun Characteristic(characteristic: Characteristic, modifier: Modifier = Modifier) {
    Log.v(TAG, "#Characteristic args : characteristic=$characteristic, modifier=$modifier")

    Column(modifier = modifier) {
        CharacteristicOverview(characteristic = characteristic)

        for (descriptor in characteristic.descriptors) {
            Descriptor(descriptor = descriptor, modifier.padding(start = 8.dp))
        }
    }
}

@Composable
@PreviewComponent
fun PreviewCharacteristic(@PreviewParameter(CharacteristicProvider::class) characteristic: Characteristic) {
    Characteristic(characteristic)
}
