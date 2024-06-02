package com.github.hemoptysisheart.ble.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.page.MainPage

@Composable
fun RootUI() {
    AndroidBleTheme {
        CompositionLocalProvider(
            LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current
        ) {
            MainPage()
        }
    }
}
