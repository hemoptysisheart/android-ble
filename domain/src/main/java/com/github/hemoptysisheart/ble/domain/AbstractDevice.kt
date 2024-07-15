package com.github.hemoptysisheart.ble.domain

abstract class AbstractDevice : Device, Comparable<Device> {
    override var connection: Connection? = null
        protected set

    override fun compareTo(other: Device): Int {
        var result = when {
            null != name && null == other.name ->
                -1

            null == name && null != other.name ->
                1

            null != name && null != other.name ->
                name!!.compareTo(other.name!!)

            else ->
                0
        }
        if (0 != result) {
            return result
        }

        result = category.compareTo(other.category)
        if (0 != result) {
            return result
        }

        return address.compareTo(other.address)
    }

    override fun equals(other: Any?) = this === other || (
            other is Device &&
                    address == other.address
            )

    override fun hashCode() = address.hashCode()

    override fun toString() = listOf(
        "name=$name",
        "address=$address",
        "category=$category",
        "services=$services",
        "rssi=$rssi",
        "connection=$connection"
    ).joinToString(", ", "Device(", ")")
}
