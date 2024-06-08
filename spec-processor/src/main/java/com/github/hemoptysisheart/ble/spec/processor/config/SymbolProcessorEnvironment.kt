package com.github.hemoptysisheart.ble.spec.processor.config

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import java.io.File

fun SymbolProcessorEnvironment.toConfig(): Config {
    return Config(
        source = Source(
            path = File(this.options["public.path"]!!)
        ),
        target = Target(codeGenerator = this.codeGenerator)
    )
}
