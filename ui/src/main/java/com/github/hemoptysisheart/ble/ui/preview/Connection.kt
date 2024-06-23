package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.domain.Connection.State
import com.github.hemoptysisheart.ble.domain.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal val PREVIEW_CONNECTION_STATE_DISCONNECTED = State(Level.DISCONNECTED)
internal val PREVIEW_CONNECTION_STATE_CONNECTING = State(Level.CONNECTING)
internal val PREVIEW_CONNECTION_STATE_CONNECTED = State(Level.CONNECTED)
internal val PREVIEW_CONNECTION_STATE_DISCONNECTING = State(Level.DISCONNECTING)

internal class PreviewConnection(
    override val level: Level,
    override val device: Device
) : Connection {
    override val state: StateFlow<State> = MutableStateFlow(State(level))

    override fun connect() = throw UnsupportedOperationException("this is preview.")
}

internal val PREVIEW_CONNECTION_DISCONNECTED = PreviewConnection(
    Level.DISCONNECTED,
    PREVIEW_DEVICE_LIST.random()
)

internal val PREVIEW_CONNECTION_CONNECTING = PreviewConnection(
    Level.CONNECTING,
    PREVIEW_DEVICE_LIST.random()
)

internal val PREVIEW_CONNECTION_CONNECTED = PreviewConnection(
    Level.CONNECTED,
    PREVIEW_DEVICE_LIST.random()
)

internal val PREVIEW_CONNECTION_DISCONNECTING = PreviewConnection(
    Level.DISCONNECTING,
    PREVIEW_DEVICE_LIST.random()
)

internal val PREVIEW_CONNECTION_LIST = listOf(
    PREVIEW_CONNECTION_DISCONNECTED,
    PREVIEW_CONNECTION_CONNECTING,
    PREVIEW_CONNECTION_CONNECTED,
    PREVIEW_CONNECTION_DISCONNECTING
)

internal class ConnectionProvider : PreviewParameterProvider<Connection?> {
    override val values: Sequence<Connection?> = sequenceOf(
        null,
        PREVIEW_CONNECTION_DISCONNECTED,
        PREVIEW_CONNECTION_CONNECTING,
        PREVIEW_CONNECTION_CONNECTED,
        PREVIEW_CONNECTION_DISCONNECTING
    )
}

class ConnectionStateProvider : PreviewParameterProvider<State> {
    override val values: Sequence<State> = sequenceOf(
        PREVIEW_CONNECTION_STATE_DISCONNECTED,
        PREVIEW_CONNECTION_STATE_CONNECTING,
        PREVIEW_CONNECTION_STATE_CONNECTED,
        PREVIEW_CONNECTION_STATE_DISCONNECTING
    )
}
