package com.github.hemoptysisheart.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.github.hemoptysisheart.android.ui.atom.AndroidTemplateTheme
import com.github.hemoptysisheart.android.ui.page.MainPage

@Composable
fun RootUI() {
    AndroidTemplateTheme {
        CompositionLocalProvider(
            LocalLifecycleOwner provides androidx.compose.ui.platform.LocalLifecycleOwner.current
        ) {
            MainPage()
        }
    }
}
