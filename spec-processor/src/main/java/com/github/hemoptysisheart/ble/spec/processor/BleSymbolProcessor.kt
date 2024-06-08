package com.github.hemoptysisheart.ble.spec.processor

import com.github.hemoptysisheart.ble.spec.annotation.BleSpecConfiguration
import com.github.hemoptysisheart.ble.spec.processor.config.Config
import com.github.hemoptysisheart.ble.spec.processor.processor.BleSpecConfigurationProcessor
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate

class BleSymbolProcessor(
    private val configuration: Config
) : SymbolProcessor {
    companion object {
        private val TAG = BleSymbolProcessor::class.simpleName
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        LOGGER.info("$TAG#process args : resolver=$resolver")

        val config = resolver.getSymbolsWithAnnotation(BleSpecConfiguration::class.qualifiedName!!)
            .filterIsInstance(KSClassDeclaration::class.java)
            .filter(KSNode::validate)
            .first()
            .annotations
            .filter { it.shortName.asString() == BleSpecConfiguration::class.simpleName }
            .first()
        LOGGER.info("$TAG#process : class=$config")

        BleSpecConfigurationProcessor(this.configuration).process(config)

        return emptyList()
    }

    override fun toString() = listOf(
        "config=$configuration"
    ).joinToString(", ", "$TAG(", ")")
}
