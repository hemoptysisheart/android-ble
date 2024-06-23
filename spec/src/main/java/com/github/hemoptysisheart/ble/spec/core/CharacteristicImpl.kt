package com.github.hemoptysisheart.ble.spec.core

import java.util.UUID

class CharacteristicImpl(
    override val uuid: UUID,
    override val descriptors: List<Descriptor>
) : Characteristic {
    override fun equals(other: Any?) = this === other || (
            other is CharacteristicImpl &&
                    uuid == other.uuid
            )

    override fun hashCode() = uuid.hashCode()

    override fun toString() = listOf(
        "uuid=$uuid",
        "descriptors=$descriptors"
    ).joinToString(", ", "CharacteristicImpl(", ")")
}