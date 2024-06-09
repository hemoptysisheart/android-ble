package com.github.hemoptysisheart.ble.ui.navigator

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import com.github.hemoptysisheart.ui.navigation.destination.BaseNavigator
import com.github.hemoptysisheart.ui.navigation.destination.Destination
import com.github.hemoptysisheart.ui.navigation.destination.Navigator

class MainNavigator(
    private val base: BaseNavigator
) : Navigator by base {
    companion object : Destination {
        const val TAG = "MainNavigator"

        override val arguments: List<NamedNavArgument> = emptyList()
        override val deepLinks: List<NavDeepLink> = emptyList()
        override val id = "main"

        override fun route(vararg arguments: Any) = if (arguments.isEmpty()) {
            id
        } else {
            throw IllegalArgumentException("MainPage does not have arguments.")
        }
    }

    override val destination: Destination = MainNavigator
}
