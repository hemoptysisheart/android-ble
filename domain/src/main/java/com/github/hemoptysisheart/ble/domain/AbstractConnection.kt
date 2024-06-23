package com.github.hemoptysisheart.ble.domain

import android.util.Log
import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.domain.Connection.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class AbstractConnection<D : AbstractDevice>(
    protected val tag: String = "AbstractConnection",
    override val device: D
) : Connection {
    @Suppress("PropertyName")
    protected val _state = MutableStateFlow(State(Level.DISCONNECTED))
    override val state: StateFlow<State> = _state

    override var level: Level = Level.DISCONNECTED
        protected set(value) {
            Log.d(tag, "#level.set : $value")
            field = value
            _state.update { it.copy(level = value) }
        }
}
