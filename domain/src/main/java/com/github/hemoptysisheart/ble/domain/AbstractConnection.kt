package com.github.hemoptysisheart.ble.domain

abstract class AbstractConnection<D : AbstractDevice>(
    override val device: D
) : Connection
