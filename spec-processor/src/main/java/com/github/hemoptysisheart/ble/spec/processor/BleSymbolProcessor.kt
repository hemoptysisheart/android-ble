package com.github.hemoptysisheart.ble.spec.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

class BleSymbolProcessor : SymbolProcessor {
    companion object {
        private val TAG = BleSymbolProcessor::class.simpleName
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        LOGGER.info("$TAG#process args : resolver=$resolver")

        return emptyList()
    }
}
