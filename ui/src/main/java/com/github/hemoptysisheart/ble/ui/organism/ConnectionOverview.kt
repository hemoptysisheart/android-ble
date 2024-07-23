package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.ui.R
import com.github.hemoptysisheart.ble.ui.molecule.PropertyMedium
import com.github.hemoptysisheart.ble.ui.resource.ConnectionLevelRes

@Composable
fun ConnectionOverview(connection: Connection.State, modifier: Modifier = Modifier) {
    Log.v(TAG, "#ConnectionOverview args : connection=$connection, modifier=$modifier")

    Column(modifier = modifier) {
        PropertyMedium(
            name = stringResource(R.string.domain_connection_level_label),
            value = stringResource(ConnectionLevelRes[connection.level].label)
        )
        PropertyMedium(
            name = stringResource(R.string.domain_connection_prop_mtu_label),
            value = connection.mtu?.let { stringResource(R.string.domain_connection_prop_mtu_format, it) }
        )
    }
}
