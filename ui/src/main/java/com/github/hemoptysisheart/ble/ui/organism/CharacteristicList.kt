package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.ui.preview.CharacteristicListProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun CharacteristicList(characteristics: List<Characteristic>, modifier: Modifier = Modifier) {
    Log.v(TAG, "#CharacteristicList args : characteristics=$characteristics, modifier=$modifier")

    Column(modifier = modifier) {
        Text(
            text = "Characteristic 목록",
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium
        )

        for (characteristic in characteristics) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 4.dp, 4.dp, 4.dp),
                thickness = 1.dp
            )
            Characteristic(
                characteristic = characteristic,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
@PreviewComponent
internal fun PreviewCharacteristicList(@PreviewParameter(CharacteristicListProvider::class) characteristics: List<Characteristic>) {
    CharacteristicList(characteristics)
}