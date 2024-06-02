package com.github.hemoptysisheart.ble.spec.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class BleSymbolProcessorProvider : SymbolProcessorProvider {
    companion object {
        private val TAG = BleSymbolProcessorProvider::class.simpleName
    }

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        LOGGER = environment.logger
        LOGGER.info("$TAG#create args : environment=$environment")

        val provider = BleSymbolProcessor()

        LOGGER.info("$TAG#create return : provider=$provider")
        return provider
    }
}
