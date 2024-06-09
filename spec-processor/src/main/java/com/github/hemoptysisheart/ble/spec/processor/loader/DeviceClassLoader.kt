package com.github.hemoptysisheart.ble.spec.processor.loader

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import com.charleskorn.kaml.decodeFromStream
import com.github.hemoptysisheart.ble.spec.processor.data.ClassOfDevice
import java.io.File

class DeviceClassLoader(
    private val file: File
) {
    fun load(): ClassOfDevice {
        val parser = Yaml(configuration = YamlConfiguration(strictMode = false))
        return parser.decodeFromStream<ClassOfDevice>(file.inputStream())
    }
}