package com.github.hemoptysisheart.ble.spec.core

import java.util.UUID

class ServiceImpl(
    override val uuid: UUID,
    override val id: String,
    override val name: String,
    override val characteristics: List<Characteristic> = emptyList()
) : Service {
    constructor(
        uuid16: Int,
        id: String,
        name: String,
        characteristics: List<Characteristic> = emptyList()
    ) : this(uuid16.toUUID(), id, name, characteristics)

    override fun equals(other: Any?) = this === other || (
            other is ServiceImpl &&
                    uuid == other.uuid
            )

    override fun hashCode() = uuid.hashCode()

    override fun toString() = listOf(
        "uuid=$uuid",
        "id=$id",
        "name=$name",
        "characteristics=$characteristics"
    ).joinToString(", ", "ServiceImpl(", ")")
}
