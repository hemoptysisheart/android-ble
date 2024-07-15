package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.domain.Connection.State
import com.github.hemoptysisheart.ble.domain.Service
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

/**
 * 연결 안됨.
 */
internal val PREVIEW_CONNECTION_STATE_DISCONNECTED = State(Level.DISCONNECTED)

/**
 * 연결중.
 */
internal val PREVIEW_CONNECTION_STATE_CONNECTING = State(Level.CONNECTING)

/**
 * 연결됨.
 */
internal val PREVIEW_CONNECTION_STATE_CONNECTED = State(
    level = Level.CONNECTED,
    services = PREVIEW_SERVICE_LIST.filter { Random.nextBoolean() }
)

/**
 * 연결 끊는 중.
 */
internal val PREVIEW_CONNECTION_STATE_DISCONNECTING = State(Level.DISCONNECTING)

internal data class PreviewConnection(
    override val level: Level,
    override val services: List<Service> = listOf(),
    override val mtu: Int? = null
) : Connection {
    override val state: StateFlow<State> = MutableStateFlow(State(level, mtu, services))

    override suspend fun requestMtu(mtu: Int): Int =
        throw UnsupportedOperationException("preview does not support this operation")
}

/**
 * 연결 안됨.
 */
val PREVIEW_CONNECTION_DISCONNECTED: Connection = PreviewConnection(
    level = Level.DISCONNECTED,
    services = listOf()
)

/**
 * 연결중.
 */
val PREVIEW_CONNECTION_CONNECTING: Connection = PreviewConnection(
    level = Level.CONNECTING,
    services = listOf()
)

/**
 * 연결됨.
 */
val PREVIEW_CONNECTION_CONNECTED: Connection = PreviewConnection(
    level = Level.CONNECTED,
    services = PREVIEW_SERVICE_LIST.filter { Random.nextBoolean() }
)

/**
 * 연결 끊는 중.
 */
val PREVIEW_CONNECTION_DISCONNECTING: Connection = PreviewConnection(
    level = Level.DISCONNECTING,
    services = listOf()
)

val PREVIEW_CONNECTION_LIST = listOf(
    PREVIEW_CONNECTION_DISCONNECTED,
    PREVIEW_CONNECTION_CONNECTING,
    PREVIEW_CONNECTION_CONNECTED,
    PREVIEW_CONNECTION_DISCONNECTING
)

val PREVIEW_CONNECTION_STATE_LIST = listOf(
    PREVIEW_CONNECTION_STATE_DISCONNECTED,
    PREVIEW_CONNECTION_STATE_CONNECTING,
    PREVIEW_CONNECTION_STATE_CONNECTED,
    PREVIEW_CONNECTION_STATE_DISCONNECTING
)

class ConnectionStateProvider : PreviewParameterProvider<State> {
    override val values: Sequence<State> = PREVIEW_CONNECTION_STATE_LIST.asSequence()
}
