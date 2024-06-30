package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.spec.core.Characteristic
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.CharacteristicTypeProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

/**
 * [Characteristic]용 UI.
 */
@Composable
fun ColumnScope.CharacteristicType(
    characteristic: Characteristic,
    modifier: Modifier = Modifier
) {
    Log.v(TAG, "#CharacteristicType args : characteristic=$characteristic, modifier=$modifier")

    characteristic.name.let { name ->
        if (null == name) {
            Text(
                text = "N/A",
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.titleSmall
            )
        } else {
            Text(
                text = name,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
    Text(
        text = "UUID : ${characteristic.uuid}",
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
@PreviewComponent
fun PreviewCharacteristicType(@PreviewParameter(CharacteristicTypeProvider::class) characteristic: Characteristic) {
    AndroidBleTheme {
        Column(Modifier.fillMaxWidth()) {
            CharacteristicType(characteristic = characteristic, modifier = Modifier.fillMaxWidth())
        }
    }
}
