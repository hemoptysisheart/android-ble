package com.github.hemoptysisheart.ble.domain

import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.domain.Connection.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class AbstractConnection<D : AbstractDevice>(
    override val device: D
) : Connection {
    @Suppress("PropertyName")
    protected val _state = MutableStateFlow(State(Level.DISCONNECTED))
    override val state: StateFlow<State> = _state

    override var level: Level = Level.DISCONNECTED
        protected set
}
