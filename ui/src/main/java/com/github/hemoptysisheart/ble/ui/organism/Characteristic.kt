package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
fun ColumnScope.Characteristic(
    characteristic: Characteristic,
    modifier: Modifier = Modifier,
    onClickNotification: (Characteristic) -> Unit = {},
    onClickIndication: (Characteristic) -> Unit = {},
    onClickRead: (Characteristic) -> Unit = {},
) {
    Log.v(TAG, "#Characteristic args : characteristic=$characteristic, modifier=$modifier")

    CharacteristicType(characteristic = characteristic.type, modifier = Modifier.fillMaxWidth())
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (characteristic.notifiable) {
            Button(onClick = { onClickNotification(characteristic) }, Modifier.padding(4.dp)) {
                Text(text = "Notification", color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        if (characteristic.indicatable) {
            Button(onClick = { onClickIndication(characteristic) }, Modifier.padding(4.dp)) {
                Text(text = "Indication", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = characteristic.readable, onCheckedChange = null, enabled = false)
        Text(text = "Readable", color = MaterialTheme.colorScheme.onBackground)
        if (characteristic.readable) {
            Spacer(modifier = Modifier.weight(1F))
            Button(onClick = { onClickRead(characteristic) }, modifier = Modifier.padding(8.dp, 0.dp)) {
                Text(
                    text = "READ",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = characteristic.writable, onCheckedChange = null, enabled = false)
        Text(text = "Writable", color = MaterialTheme.colorScheme.onBackground)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = characteristic.writableWithoutResponse, onCheckedChange = null, enabled = false)
        Text(text = "Writable without Response", color = MaterialTheme.colorScheme.onBackground)
    }

    if (characteristic.descriptors.isEmpty()) {
        Text(text = "No Descriptor", color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.fillMaxWidth())
    } else for (descriptor in characteristic.descriptors) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${descriptor.type.name}${if (descriptor.readable) ", readable" else ", not-readable"}${if (descriptor.writable) ", writable" else ", not-writable"}",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth()
        )
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
