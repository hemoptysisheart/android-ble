package com.github.hemoptysisheart.ble.spec.core

import java.util.UUID

class ServiceImpl(
    override val uuid: UUID
) : Service {
    override fun equals(other: Any?) = this === other || (
            other is ServiceImpl &&
                    uuid == other.uuid
            )

    override fun hashCode() = uuid.hashCode()

    override fun toString() = listOf(
        "uuid=$uuid"
    ).joinToString(", ", "ServiceImpl(", ")")
}
