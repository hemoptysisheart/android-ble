package com.github.hemoptysisheart.ble.model

import com.github.hemoptysisheart.ble.domain.AbstractDevice
import com.github.hemoptysisheart.ble.spec.core.DeviceClass
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.isRootTest
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.Duration
import java.time.Instant
import kotlin.random.Random

class DeviceCacheModelImplTest : BehaviorSpec() {
    private val logger = KotlinLogging.logger { }

    private lateinit var model: DeviceCacheModelImpl

    init {
        beforeTest {
            if (it.isRootTest()) {
                model = DeviceCacheModelImpl()
                logger.info { "[SETUP] model=$model" }
            }
        }

        given("ttl - 유효시간이 지나지 않으면 캐시에 접근할 수 있다.") {
            val address = "00:%02X".format(Random.nextBytes(1)[0])
            val device = object : AbstractDevice() {
                override val name = "name #$address"
                override val address = address
                override val category = DeviceClass.entries.random()
                override val rssi: Int = -100
            }
            logger.info { "[GIVEN] address='$address', device=$device" }

            model.cache(listOf(device))
            logger.info { "[GIVEN] model=$model" }

            `when`("기기를 요청하면") {
                val cached = model[address]
                logger.info { "[WHEN] cached=$cached" }

                then("캐시된 기기를 반환한다.") {
                    cached shouldBe device

                    model.cached.shouldBeTrue()
                    model.expireAt shouldNotBe Instant.MIN
                    model.devices shouldBe listOf(device)
                }
            }
        }

        given("ttl - 지정한 유효시간이 지나면 캐시가 삭제되는 것을 확인한다.") {
            val ttl = Duration.ofMillis(1000L)
            model = DeviceCacheModelImpl(ttl = ttl)

            val device = object : AbstractDevice() {
                override val name = "name"
                override val address: String = "00:00"
                override val category: DeviceClass = DeviceClass.entries.random()
                override val rssi: Int = -100
            }
            logger.info { "[GIVEN] ttl=$ttl, device=$device" }

            model.cache(listOf(device))
            logger.info { "[GIVEN] model=$model" }

            `when`("시간이 지나면") {
                Thread.sleep(ttl.toMillis() + 1L)
                logger.info { "[WHEN] model=$model" }

                then("캐시가 삭제된다.") {
                    model.cached.shouldBeFalse()
                    model.devices.shouldBeEmpty()
                    model.expireAt shouldBe Instant.MIN
                }
            }
        }
    }
}
