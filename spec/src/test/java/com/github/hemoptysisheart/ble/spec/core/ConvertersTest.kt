package com.github.hemoptysisheart.ble.spec.core

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothClass.Device
import android.bluetooth.BluetoothClass.Service
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ConvertersTest : BehaviorSpec() {
    private val logger = KotlinLogging.logger { }

    init {
        table(
            headers("bluetoothClass", "expected"),
            row(Service.LIMITED_DISCOVERABILITY, MajorServiceClass.LIMITED_DISCOVERABLE_MODE),
            row(Service.LE_AUDIO, MajorServiceClass.LE_AUDIO),
            row(Service.POSITIONING, MajorServiceClass.POSITIONING),
            row(Service.NETWORKING, MajorServiceClass.NETWORKING),
            row(Service.RENDER, MajorServiceClass.RENDERING),
            row(Service.CAPTURE, MajorServiceClass.CAPTURING),
            row(Service.OBJECT_TRANSFER, MajorServiceClass.OBJECT_TRANSFER),
            row(Service.AUDIO, MajorServiceClass.AUDIO),
            row(Service.TELEPHONY, MajorServiceClass.TELEPHONY),
            row(Service.INFORMATION, MajorServiceClass.INFORMATION)
        ).forAll { bluetoothClass, expected ->
            given("MajorServiceClass - Android의 BluetoothClass를 ServiceClass로 변환하기.") {
                val bleClass = mockk<BluetoothClass>()
                every { bleClass.hasService(bluetoothClass) } returns true
                every { bleClass.hasService(not(bluetoothClass)) } returns false
                logger.info { "[GIVEN] bluetoothClass=$bluetoothClass, expected=$expected(${expected.mask}), bleClass=$bleClass" }

                `when`("변환하면") {
                    val actual = MajorServiceClass(bleClass)
                    logger.info { "[WHEN] actual=$actual" }

                    then("예상값과 같아야 한다.") {
                        actual.size shouldBe 1
                        actual shouldContain expected
                    }
                }
            }
        }

        table(
            headers("serviceClasses", "expected"),
            row(
                listOf(Service.AUDIO, Service.POSITIONING),
                setOf(MajorServiceClass.AUDIO, MajorServiceClass.POSITIONING)
            ),
            row(
                listOf(Service.NETWORKING, Service.RENDER),
                setOf(MajorServiceClass.NETWORKING, MajorServiceClass.RENDERING)
            )
        ).forAll { serviceClasses, expected ->
            given("MajorServiceClass - 여러 서비스를 제공하는 디바이스를 MajorServiceClass로 변환하기.") {
                logger.info { "[GIVEN] serviceClasses=${serviceClasses.map { it.toString(16) }}, expected=$expected" }

                val bleClass = mockk<BluetoothClass>()
                every { bleClass.hasService(any()) } returns false
                for (svc in serviceClasses) {
                    every { bleClass.hasService(svc) } returns true
                }
                logger.info { "[GIVEN] bleClass=$bleClass" }

                `when`("변환하면") {
                    val actual = MajorServiceClass(bleClass)
                    logger.info { "[WHEN] actual=$actual" }

                    then("예상값과 같아야 한다.") {
                        actual.size shouldBe expected.size
                        actual shouldContainAll expected
                    }
                }
            }
        }

        table(
            headers("bluetoothClass", "expected"),
            row(0, MajorDeviceClass.MISCELLANEOUS),
            row(Device.COMPUTER_UNCATEGORIZED, MajorDeviceClass.COMPUTER),
            row(Device.COMPUTER_LAPTOP, MajorDeviceClass.COMPUTER),
            row(Device.PHONE_UNCATEGORIZED, MajorDeviceClass.PHONE),
            row(Device.PHONE_MODEM_OR_GATEWAY, MajorDeviceClass.PHONE),
            row(Device.AUDIO_VIDEO_WEARABLE_HEADSET, MajorDeviceClass.AUDIO_VIDEO),
            row(Device.AUDIO_VIDEO_VIDEO_GAMING_TOY, MajorDeviceClass.AUDIO_VIDEO),
            row(Device.WEARABLE_UNCATEGORIZED, MajorDeviceClass.WEARABLE),
            row(Device.WEARABLE_GLASSES, MajorDeviceClass.WEARABLE),
            row(Device.TOY_VEHICLE, MajorDeviceClass.TOY),
            row(Device.TOY_DOLL_ACTION_FIGURE, MajorDeviceClass.TOY),
            row(Device.HEALTH_WEIGHING, MajorDeviceClass.HEALTH),
            row(Device.HEALTH_DATA_DISPLAY, MajorDeviceClass.HEALTH),
            row(Device.PERIPHERAL_NON_KEYBOARD_NON_POINTING, MajorDeviceClass.PERIPHERAL),
            row(Device.PERIPHERAL_KEYBOARD, MajorDeviceClass.PERIPHERAL)
        ).forAll { value, expected ->
            given("DeviceClass - Android의 BluetoothClass를 DeviceClass로 변환하기.") {
                val bluetoothClass = mockk<BluetoothClass>()
                every { bluetoothClass.majorDeviceClass } returns (value and 0x1F00)
                every { bluetoothClass.deviceClass } returns (value and 0x1FFC)
                logger.info { "[GIVEN] bluetoothClass=$bluetoothClass" }

                `when`("변환하면") {
                    val actual = MajorDeviceClass(bluetoothClass)
                    logger.info { "[WHEN] actual=$actual" }

                    then("예상값과 같아야 한다.") {
                        actual shouldBe expected
                    }
                }
            }
        }
    }
}
