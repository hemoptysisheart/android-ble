package com.github.hemoptysisheart.ble.model

import com.github.hemoptysisheart.ble.domain.AbstractConnection
import com.github.hemoptysisheart.ble.domain.Connection

class Connection(device: Device) : AbstractConnection<Device>(device) {
    companion object {
        internal const val TAG = "Connection"
    }

    override var state: Connection.State = Connection.State.DISCONNECTED
        private set

    override fun toString() = listOf(
        "device=$device",
        "state=$state"
    ).joinToString(", ", "$TAG(", ")")
}
