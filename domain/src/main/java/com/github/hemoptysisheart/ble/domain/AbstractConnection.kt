package com.github.hemoptysisheart.ble.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class AbstractConnection(
    protected val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : Connection {
    override var state: Connection.State = Connection.State.DISCONNECTED
        protected set

    override fun toString() = listOf(
        "state=$state"
    ).joinToString(", ", "${this::class.simpleName}(", ")")
}
