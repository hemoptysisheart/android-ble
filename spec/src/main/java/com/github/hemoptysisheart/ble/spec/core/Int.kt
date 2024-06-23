package com.github.hemoptysisheart.ble.spec.core

import java.util.UUID

/**
 * 16bit UUID를 128bit UUID로 변환한다.
 */
fun Int.toUUID(): UUID = UUID.fromString(String.format("%08x-0000-1000-8000-00805F9B34FB", this).uppercase())
