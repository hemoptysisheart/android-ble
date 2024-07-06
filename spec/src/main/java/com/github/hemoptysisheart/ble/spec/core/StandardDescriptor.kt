package com.github.hemoptysisheart.ble.spec.core

import java.util.UUID

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers)에 등록된 디스크립터.
 */
class StandardDescriptor(
    override val uuid: UUID,
    override val id: String,
    override val name: String
) : Descriptor {
    constructor(uuid: Int, id: String, name: String) : this(uuid.toUUID(), id, name)

    override fun equals(other: Any?) = this === other || (
            other is StandardDescriptor &&
                    uuid == other.uuid
            )

    override fun hashCode() = uuid.hashCode()

    override fun toString() = listOf(
        "uuid=$uuid",
        "id=$id",
        "name=$name"
    ).joinToString(", ", "DescriptorImpl(", ")")
}
