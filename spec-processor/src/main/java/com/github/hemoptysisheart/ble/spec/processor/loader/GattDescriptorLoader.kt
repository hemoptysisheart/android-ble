package com.github.hemoptysisheart.ble.spec.processor.loader

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import com.charleskorn.kaml.decodeFromStream
import com.github.hemoptysisheart.ble.spec.processor.data.GattStandardAttributeList
import java.io.File

class GattDescriptorLoader(private val file: File) {
    fun load(): GattStandardAttributeList {
        val parser = Yaml(configuration = YamlConfiguration(strictMode = false))
        return parser.decodeFromStream<GattStandardAttributeList>(file.inputStream())
    }
}