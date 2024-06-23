package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.Connection.State
import com.github.hemoptysisheart.ble.domain.ConnectionState
import com.github.hemoptysisheart.ble.domain.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

val PREVIEW_CONNECTION_DISCONNECTED = object : Connection {
    override val state: StateFlow<State> = MutableStateFlow(State(ConnectionState.DISCONNECTED))
    override val device: Device = PREVIEW_DEVICE_LIST.random()
}

val PREVIEW_CONNECTION_CONNECTING = object : Connection {
    override val state: StateFlow<State> = MutableStateFlow(State(ConnectionState.CONNECTING))
    override val device: Device = PREVIEW_DEVICE_LIST.random()
}

val PREVIEW_CONNECTION_CONNECTED = object : Connection {
    override val state: StateFlow<State> = MutableStateFlow(State(ConnectionState.CONNECTED))
    override val device: Device = PREVIEW_DEVICE_LIST.random()
}

val PREVIEW_CONNECTION_DISCONNECTING = object : Connection {
    override val state: StateFlow<State> = MutableStateFlow(State(ConnectionState.DISCONNECTING))
    override val device: Device = PREVIEW_DEVICE_LIST.random()
}

val PREVIEW_CONNECTION_LIST = listOf(
    PREVIEW_CONNECTION_DISCONNECTED,
    PREVIEW_CONNECTION_CONNECTING,
    PREVIEW_CONNECTION_CONNECTED,
    PREVIEW_CONNECTION_DISCONNECTING
)

class ConnectionProvider : PreviewParameterProvider<Connection?> {
    override val values = sequenceOf(
        null,
        PREVIEW_CONNECTION_DISCONNECTED,
        PREVIEW_CONNECTION_CONNECTING,
        PREVIEW_CONNECTION_CONNECTED,
        PREVIEW_CONNECTION_DISCONNECTING
    )
}

class ConnectionStateProvider : PreviewParameterProvider<State> {
    override val values = sequenceOf(
        PREVIEW_CONNECTION_DISCONNECTED.state.value,
        PREVIEW_CONNECTION_CONNECTING.state.value,
        PREVIEW_CONNECTION_CONNECTED.state.value,
        PREVIEW_CONNECTION_DISCONNECTING.state.value
    )
}
