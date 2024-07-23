package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.ui.R
import com.github.hemoptysisheart.ble.ui.molecule.PropertySmall

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
    }
}
