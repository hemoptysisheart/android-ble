package com.github.hemoptysisheart.ble.ui.resource

import androidx.annotation.StringRes
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.ui.R

enum class ConnectionLevelRes(
    val domain: Connection.Level,
    @StringRes val label: Int
) {
    DISCONNECTED(Connection.Level.DISCONNECTED, R.string.domain_connection_level_value_disconnected_label),
    CONNECTING(Connection.Level.CONNECTING, R.string.domain_connection_level_value_connecting_label),
    CONNECTED(Connection.Level.CONNECTED, R.string.domain_connection_level_value_connected_label),
    DISCONNECTING(Connection.Level.DISCONNECTING, R.string.domain_connection_level_value_disconnecting_label);

    companion object {
        operator fun get(level: Connection.Level) = entries.first { it.domain == level }
    }
}