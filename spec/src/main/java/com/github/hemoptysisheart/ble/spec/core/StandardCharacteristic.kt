package com.github.hemoptysisheart.ble.spec.core

import java.util.UUID

class StandardCharacteristic internal constructor(
    override val uuid: UUID,
    override val id: String,
    override val name: String
) : Characteristic {
    internal constructor(
        uuid: Int,
        id: String,
        name: String
    ) : this(uuid.toUUID(), id, name)

    override fun equals(other: Any?) = this === other || (
            other is StandardCharacteristic &&
                    uuid == other.uuid
            )

    override fun hashCode() = uuid.hashCode()

    override fun toString() = listOf(
        "uuid=$uuid",
        "id=$id",
        "name='$name'"
    ).joinToString(", ", "StandardCharacteristic(", ")")
}