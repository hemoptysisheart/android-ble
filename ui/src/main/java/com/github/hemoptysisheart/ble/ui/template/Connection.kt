package com.github.hemoptysisheart.ble.ui.template

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.molecule.ConnectionState
import com.github.hemoptysisheart.ble.ui.preview.ConnectionProvider

@Composable
fun ColumnScope.Connection(connection: Connection?, modifier: Modifier = Modifier) {
    Log.v(TAG, "#Connection args : connection=$connection, modifier=$modifier")

    if (null == connection) {
        Text("연결 정보가 없습니다.", modifier = modifier)
    } else {
        ConnectionState(state = connection.state.value)
    }
}


@Composable
@PreviewFontScale
@PreviewLightDark
fun ConnectionPreview(@PreviewParameter(ConnectionProvider::class) connection: Connection?) {
    AndroidBleTheme {
        Column {
            Connection(connection)
        }
    }
}
