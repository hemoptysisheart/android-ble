package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.CharacteristicListProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun CharacteristicList(
    characteristics: List<Characteristic>,
    modifier: Modifier = Modifier,
    onClickRead: (Characteristic) -> Unit = {}
) {
    Log.v(
        TAG,
        "#CharacteristicList args : characteristics=$characteristics, modifier=$modifier, onClickRead=$onClickRead"
    )

    if (characteristics.isEmpty()) {
        CharacteristicListEmpty(modifier = modifier)
    } else {
        CharacteristicListNotEmpty(characteristics = characteristics, modifier = modifier, onClickRead = onClickRead)
    }
}

@Composable
fun CharacteristicListEmpty(modifier: Modifier = Modifier) {
    Text(
        text = "캐릭터리스틱 목록 정보가 없습니다.",
        modifier = modifier,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
fun CharacteristicListNotEmpty(
    characteristics: List<Characteristic>,
    modifier: Modifier = Modifier,
    onClickRead: (Characteristic) -> Unit = {}
) {
    Column(modifier = modifier) {
        for (characteristic in characteristics) {
            Spacer(modifier = Modifier.height(8.dp))
            Characteristic(
                characteristic = characteristic,
                modifier = Modifier.fillMaxWidth(),
                onClickRead = onClickRead
            )
        }
    }
}

@Composable
@PreviewComponent
fun PreviewCharacteristicList(
    @PreviewParameter(CharacteristicListProvider::class) characteristics: List<Characteristic>
) {
    AndroidBleTheme {
        CharacteristicList(
            characteristics = characteristics,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}