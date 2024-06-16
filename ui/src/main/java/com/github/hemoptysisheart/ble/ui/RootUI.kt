package com.github.hemoptysisheart.ble.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.MainNavigator
import com.github.hemoptysisheart.ble.ui.navigator.RequestScanNavigator
import com.github.hemoptysisheart.ble.ui.navigator.ScanNavigator
import com.github.hemoptysisheart.ble.ui.navigator.SplashNavigator
import com.github.hemoptysisheart.ble.ui.page.MainPage
import com.github.hemoptysisheart.ble.ui.page.RequestScanPage
import com.github.hemoptysisheart.ble.ui.page.ScanPage
import com.github.hemoptysisheart.ble.ui.page.SplashPage
import com.github.hemoptysisheart.ui.navigation.compose.NavigationGraph
import com.github.hemoptysisheart.ui.navigation.compose.page
import com.github.hemoptysisheart.ui.navigation.destination.BaseNavigator

@Composable
fun RootUI(
    baseNavigator: BaseNavigator
) {
    Log.v("ui", "#RootUI args : baseNavigator=$baseNavigator")
    AndroidBleTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            NavigationGraph(
                navHostController = baseNavigator.navHostController,
                startDestinationId = baseNavigator.startDestination.id
            ) {
                page(SplashNavigator(baseNavigator)) { navigator ->
                    SplashPage(navigator)
                }

                page(RequestScanNavigator(baseNavigator)) { navigator ->
                    RequestScanPage(navigator)
                }

                page(MainNavigator(baseNavigator)) { navigator ->
                    MainPage(navigator)
                }

                page(ScanNavigator(baseNavigator)) { navigator ->
                    ScanPage(navigator)
                }
            }
        }
    }
}
