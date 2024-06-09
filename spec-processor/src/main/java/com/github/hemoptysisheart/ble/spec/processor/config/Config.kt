package com.github.hemoptysisheart.ble.spec.processor.config

data class Config(
    val source: Source,
    @Deprecated("https://github.com/hemoptysisheart/android-ble/issues/4")
    val target: Target
)
