package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.CharacteristicTypeListProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun CharacteristicList(characteristics: List<Characteristic>, modifier: Modifier = Modifier) {
    Log.v(TAG, "#CharacteristicList args : characteristics=$characteristics, modifier=$modifier")

    if (characteristics.isEmpty()) {
        CharacteristicListEmpty(modifier = modifier)
    } else {
        CharacteristicListNotEmpty(characteristics = characteristics, modifier = modifier)
    }
}

@Composable
fun CharacteristicListEmpty(modifier: Modifier = Modifier) {
    Text(text = "캐릭터리스틱 목록 정보가 없습니다.", modifier = modifier, style = MaterialTheme.typography.titleSmall)
}

@Composable
fun CharacteristicListNotEmpty(characteristics: List<Characteristic>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        for (characteristic in characteristics) {
            CharacteristicType(characteristic = characteristic.type, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
@PreviewComponent
fun PreviewCharacteristicList(
    @PreviewParameter(CharacteristicTypeListProvider::class) characteristics: List<Characteristic>
) {
    AndroidBleTheme {
        CharacteristicList(characteristics = characteristics, modifier = Modifier.fillMaxWidth())
    }
}