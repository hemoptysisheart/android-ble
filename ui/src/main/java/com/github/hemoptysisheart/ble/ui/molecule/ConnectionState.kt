package com.github.hemoptysisheart.ble.ui.molecule

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.ConnectionState
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.preview.ConnectionStateProvider

@Composable
fun ConnectionState(state: ConnectionState, modifier: Modifier = Modifier) {
    Log.v(TAG, "#ConnectionState args : state=$state, modifier=$modifier")

    Text(text = "상태 : ${state}", modifier = modifier)
}

@Composable
@PreviewFontScale
@PreviewLightDark
fun ConnectionStatePreview(@PreviewParameter(ConnectionStateProvider::class) state: ConnectionState) {
    AndroidBleTheme {
        ConnectionState(state)
    }
}
