package com.github.hemoptysisheart.ble.ui

import androidx.compose.runtime.Composable
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.SplashNavigator
import com.github.hemoptysisheart.ble.ui.page.SplashPage
import com.github.hemoptysisheart.ui.navigation.compose.NavigationGraph
import com.github.hemoptysisheart.ui.navigation.compose.page
import com.github.hemoptysisheart.ui.navigation.destination.BaseNavigator

@Composable
fun RootUI(
    baseNavigator: BaseNavigator
) {
    AndroidBleTheme {
        NavigationGraph(
            navHostController = baseNavigator.navHostController,
            startDestinationId = baseNavigator.startDestination.id
        ) {
            page(SplashNavigator(baseNavigator)) {
                SplashPage(navigator = it)
            }
        }
    }
}
