package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.CharacteristicProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun ColumnScope.Characteristic(characteristic: Characteristic, modifier: Modifier = Modifier) {
    Log.v(TAG, "#Characteristic args : characteristic=$characteristic, modifier=$modifier")

    CharacteristicType(characteristic = characteristic.type, modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 2.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Readable", color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.weight(1F))
        Checkbox(checked = characteristic.readable, onCheckedChange = null, enabled = false)
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 2.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Writable", color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.weight(1F))
        Checkbox(checked = characteristic.writable, onCheckedChange = null, enabled = false)
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 2.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Writable without Response", color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.weight(1F))
        Checkbox(checked = characteristic.writableWithoutResponse, onCheckedChange = null, enabled = false)
    }
}

@Composable
@PreviewComponent
internal fun PreviewCharacteristic(@PreviewParameter(CharacteristicProvider::class) characteristic: Characteristic) {
    AndroidBleTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Characteristic(characteristic, Modifier.fillMaxWidth())
        }
    }
}
