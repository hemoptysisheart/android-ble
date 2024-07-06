package com.github.hemoptysisheart.ble.domain

/**
 * 각 바이트를 16진수 문자열로 변환합니다.
 */
fun ByteArray.toHexaString(): String = joinToString(" ", "[", "]") { "%02X".format(it) }
