package com.github.hemoptysisheart.ble.spec.core

interface Characteristic : Attribute {
    val descriptors: List<Descriptor>
}
