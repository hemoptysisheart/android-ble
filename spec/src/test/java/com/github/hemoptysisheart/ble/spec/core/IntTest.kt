package com.github.hemoptysisheart.ble.spec.core

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import java.util.UUID

class IntTest : BehaviorSpec() {
    private val logger = KotlinLogging.logger { }

    init {
        table(
            headers("value", "uuid"),
            row(0x1800, UUID.fromString("00001800-0000-1000-8000-00805f9b34fb")),
            row(0xFFF0, UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")),
            row(0xAE00, UUID.fromString("0000ae00-0000-1000-8000-00805f9b34fb"))
        ).forAll { value, expected ->
            given("toUUID - 16bit => 128bit") {
                logger.info { "[GIVEN] value=$value, expected=$expected" }
                `when`("UUID로 변환하면") {
                    val actual = value.toUUID()
                    logger.info { "[WHEN] actual=$actual" }

                    then("기대한 값이 나온다.") {
                        actual shouldBe expected
                    }
                }
            }
        }
    }
}
