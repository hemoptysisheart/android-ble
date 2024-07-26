package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.ui.R
import com.github.hemoptysisheart.ble.ui.molecule.PropertySmall
import com.github.hemoptysisheart.ble.ui.preview.CharacteristicProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun CharacteristicOverview(characteristic: Characteristic, modifier: Modifier = Modifier) {
    Log.v(TAG, "#CharacteristicOverview args : characteristic=$characteristic, modifier=$modifier")

    Column(modifier = modifier) {
        PropertySmall(
            name = stringResource(R.string.domain_characteristic_type_prop_name_label),
            value = characteristic.type.name
        )
        PropertySmall(
            name = stringResource(R.string.domain_characteristic_type_prop_uuid_label),
            value = characteristic.type.uuid
        )
        PropertySmall(
            name = stringResource(R.string.domain_characteristic_type_prop_id_label),
            value = characteristic.type.id
        )

        Row {
            characteristic.readable.let { readable ->
                InputChip(
                    selected = readable,
                    onClick = { },
                    label = {
                        Text(
                            text = stringResource(
                                R.string.domain_characteristic_prop_readable_label,
                                if (readable) "✔️ " else ""
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
            characteristic.writable.let { writable ->
                InputChip(
                    selected = writable,
                    onClick = { },
                    label = {
                        Text(
                            text = stringResource(
                                R.string.domain_characteristic_prop_writable_label,
                                if (writable) "✔️ " else ""
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
            characteristic.writableWithoutResponse.let { writableWithoutResponse ->
                InputChip(
                    selected = writableWithoutResponse,
                    onClick = { },
                    label = {
                        Text(
                            text = stringResource(
                                R.string.domain_characteristic_prop_writable_without_response_label,
                                if (writableWithoutResponse) "✔️ " else ""
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
@PreviewComponent
internal fun PreviewCharacteristicOverview(
    @PreviewParameter(CharacteristicProvider::class) characteristic: Characteristic
) {
    CharacteristicOverview(characteristic)
}
