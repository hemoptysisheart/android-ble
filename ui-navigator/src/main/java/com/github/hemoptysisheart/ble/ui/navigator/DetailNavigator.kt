package com.github.hemoptysisheart.ble.ui.navigator

import androidx.compose.runtime.Immutable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ui.navigation.destination.BaseNavigator
import com.github.hemoptysisheart.ui.navigation.destination.Destination
import com.github.hemoptysisheart.ui.navigation.destination.Navigator

@Immutable
class DetailNavigator(
    private val base: BaseNavigator
) : Navigator by base {
    companion object : Destination {
        const val ARG_ADDRESS = "address"

        override val id = "detail/{$ARG_ADDRESS}"

        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(ARG_ADDRESS) {
                nullable = false
                type = NavType.StringType
            }
        )

        override val deepLinks: List<NavDeepLink> = emptyList()

        override fun route(vararg arguments: Any) = when (arguments.size) {
            1 ->
                route(arguments[0] as Device)

            else ->
                throw IllegalArgumentException("DetailPage requires 1 argument.")
        }

        fun route(device: Device): String = "detail/${device.address}"

        override fun toString() = "id=$id"
    }

    override val destination: Destination = Companion
}