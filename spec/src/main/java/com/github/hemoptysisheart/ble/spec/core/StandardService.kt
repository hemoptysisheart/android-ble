package com.github.hemoptysisheart.ble.spec.core

import java.util.UUID

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers)에 등록된 서비스.
 */
class StandardService internal constructor(
    override val uuid: UUID,
    override val id: String,
    override val name: String
) : Service {
    internal constructor(
        uuid16: Int,
        id: String,
        name: String
    ) : this(uuid16.toUUID(), id, name)

    override fun equals(other: Any?) = this === other || (
            other is StandardService &&
                    uuid == other.uuid
            )

    override fun hashCode() = uuid.hashCode()

    override fun toString() = listOf(
        "uuid=$uuid",
        "id=$id",
        "name=$name"
    ).joinToString(", ", "ServiceImpl(", ")")
}
