package com.github.hemoptysisheart.ble.ui.navigator

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ui.navigation.destination.BaseNavigator
import com.github.hemoptysisheart.ui.navigation.destination.Destination
import com.github.hemoptysisheart.ui.navigation.destination.Navigator

@Immutable
class ScanNavigator(
    private val base: BaseNavigator
) : Navigator by base {
    companion object : Destination {
        private const val TAG = "ScanNavigator"

        override val arguments: List<NamedNavArgument> = emptyList()

        override val deepLinks: List<NavDeepLink> = emptyList()

        override val id = "scan"

        override fun route(vararg arguments: Any) = if (arguments.isEmpty()) {
            id
        } else {
            throw IllegalArgumentException("ScanPage does not have arguments.")
        }

        override fun toString() = "id=$id"
    }

    override val destination: Destination = Companion

    fun detail(device: Device) {
        Log.d(TAG, "#detail args : device=$device")

        base.navHostController.navigate(DetailNavigator.route(device))
    }

    override fun toString() = listOf(
        "destination=$destination"
    ).joinToString(", ", "$TAG(", ")")
}
