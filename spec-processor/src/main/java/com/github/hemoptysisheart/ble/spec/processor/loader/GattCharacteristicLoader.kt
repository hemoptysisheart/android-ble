package com.github.hemoptysisheart.ble.spec.processor.loader

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import com.charleskorn.kaml.decodeFromStream
import com.github.hemoptysisheart.ble.spec.processor.data.GattStandardAttributeList
import java.io.File

/**
 * [`service_uuids.yaml`](https://bitbucket.org/bluetooth-SIG/public/src/main/assigned_numbers/uuids/service_uuids.yaml) 로더.
 *
 * 참고 :
 * - Bluetooth Core Specification [Vol 3] Part G, Section 3.3.1
 */
class GattCharacteristicLoader(
    private val file: File
) {
    fun load(): GattStandardAttributeList {
        val parser = Yaml(configuration = YamlConfiguration(strictMode = false))
        return parser.decodeFromStream<GattStandardAttributeList>(file.inputStream())
    }
}
