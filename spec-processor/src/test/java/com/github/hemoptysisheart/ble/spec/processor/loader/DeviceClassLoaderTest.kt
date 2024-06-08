package com.github.hemoptysisheart.ble.spec.processor.loader

import com.github.hemoptysisheart.ble.spec.processor.util.repositoryRootDir
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.BehaviorSpec
import java.nio.file.Paths

class DeviceClassLoaderTest : BehaviorSpec() {
    private val logger = KotlinLogging.logger { }

    init {
        given("파일 경로로 로더를 만들어서") {
            val file = Paths.get(
                repositoryRootDir.absolutePath,
                "ble-spec/assigned_numbers/core/class_of_device.yaml"
            ).toFile()

            val loader = DeviceClassLoader(file)
            logger.info { "[GIVEN] file=$file, loader=$loader" }

            `when`("사양을 읽으면") {
                val classOfDevice = loader.load()
                logger.info { "[WHEN] classOfDevice=$classOfDevice" }

                then("목록을 읽을 수 있다.") {
                    // OK
                }
            }
        }
    }
}
