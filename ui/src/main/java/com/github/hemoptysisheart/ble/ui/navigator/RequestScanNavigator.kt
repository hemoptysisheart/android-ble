package com.github.hemoptysisheart.ble.ui.navigator

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import com.github.hemoptysisheart.ui.navigation.destination.BaseNavigator
import com.github.hemoptysisheart.ui.navigation.destination.Destination
import com.github.hemoptysisheart.ui.navigation.destination.Navigator


@Immutable
class RequestScanNavigator(
    private val base: BaseNavigator
) : Navigator by base {
    companion object : Destination {
        private const val TAG = "RequestScanNavigator"

        override val arguments: List<NamedNavArgument> = emptyList()
        override val deepLinks: List<NavDeepLink> = emptyList()
        override val id = "request/scan"

        override fun route(vararg arguments: Any) = if (arguments.isEmpty()) {
            id
        } else {
            throw IllegalArgumentException("RequestScanPage does not have arguments.")
        }
    }

    override val destination = Companion

    fun main() {
        Log.d(TAG, "#main called.")

        base.navHostController.popBackStack()
        base.navHostController.navigate(MainNavigator.route())
    }

    fun settings() {
        Log.d(TAG, "#settings called.")

        val intent: Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.setData(Uri.fromParts("package", base.activity.packageName, null))
        base.activity.startActivity(intent)
    }
}
