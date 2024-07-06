package com.github.hemoptysisheart.ble.spec.core

import java.util.UUID

data class CustomDescriptor(
    override val uuid: UUID,
    override val name: String? = null,
    override val id: String? = null
) : Descriptor