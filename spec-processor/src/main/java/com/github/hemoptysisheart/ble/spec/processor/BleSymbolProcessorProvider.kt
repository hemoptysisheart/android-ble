package com.github.hemoptysisheart.ble.spec.processor

import com.github.hemoptysisheart.ble.spec.processor.config.toConfig
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class BleSymbolProcessorProvider : SymbolProcessorProvider {
    companion object {
        private const val TAG = "BleSymbolProcessorProvider"
    }

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        LOGGER = environment.logger
        environment.options.forEach { (key, value) ->
            LOGGER.info("$TAG#create option : $key=$value")
        }
        val codeGenerator = environment.codeGenerator
        val config = environment.toConfig()
        LOGGER.info("$TAG#create : config=$config, codeGenerator=$codeGenerator")

        val provider = BleSymbolProcessor(config)

        LOGGER.info("$TAG#create return : provider=$provider")
        return provider
    }
}
