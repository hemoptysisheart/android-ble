package com.github.hemoptysisheart.ble.spec.core

import java.util.UUID

class DescriptorImpl(
    override val uuid: UUID
) : Descriptor {
    override fun equals(other: Any?) = this === other || (
            other is DescriptorImpl &&
                    uuid == other.uuid
            )

    override fun hashCode() = uuid.hashCode()

    override fun toString() = listOf(
        "uuid=$uuid"
    ).joinToString(", ", "DescriptorImpl(", ")")
}