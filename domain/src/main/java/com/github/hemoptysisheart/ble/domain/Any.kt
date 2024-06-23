package com.github.hemoptysisheart.ble.domain

/**
 * [toString]이 복잡한 경우 사용할 기본 [toString] 구현.
 */
fun Any.defaultToString() = "${this::class.simpleName}@${Integer.toHexString(this.hashCode())}"
