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
            given("DeviceClass - Android의 BluetoothClass를 MajorDeviceClass로 변환하기.") {
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

        table(
            headers("bluetoothClass", "expected"),
            row(0, DeviceClass.MISCELLANEOUS_MISCELLANEOUS),
            row(Device.COMPUTER_UNCATEGORIZED, DeviceClass.COMPUTER_UNCATEGORIZED),
            row(Device.COMPUTER_DESKTOP, DeviceClass.COMPUTER_DESKTOP_WORKSTATION),
            row(Device.COMPUTER_WEARABLE, DeviceClass.COMPUTER_WEARABLE_COMPUTER),
            row(Device.PHONE_UNCATEGORIZED, DeviceClass.PHONE_UNCATEGORIZED),
            row(Device.PHONE_CELLULAR, DeviceClass.PHONE_CELLULAR),
            row(0b0011_0000_0000, DeviceClass.LAN_NETWORK_FULLY_AVAILABLE),
            row(0b0011_0000_0000 + (1 shl 5), DeviceClass.LAN_NETWORK_1__TO_17__UTILIZED),
            row(0b0011_0000_0000 + (7 shl 5), DeviceClass.LAN_NETWORK_NO_SERVICE_AVAILABLE),
            row(Device.AUDIO_VIDEO_UNCATEGORIZED, DeviceClass.AUDIO_VIDEO_UNCATEGORIZED),
            row(Device.AUDIO_VIDEO_WEARABLE_HEADSET, DeviceClass.AUDIO_VIDEO_WEARABLE_HEADSET_DEVICE),
            row(Device.AUDIO_VIDEO_VIDEO_GAMING_TOY, DeviceClass.AUDIO_VIDEO_GAMING_TOY),
            row(Device.PERIPHERAL_NON_KEYBOARD_NON_POINTING, DeviceClass.PERIPHERAL_UNCATEGORIZED),
            row(Device.PERIPHERAL_KEYBOARD, DeviceClass.PERIPHERAL_KEYBOARD),
            row(Device.PERIPHERAL_POINTING, DeviceClass.PERIPHERAL_POINTING_DEVICE),
            row(Device.PERIPHERAL_KEYBOARD_POINTING, DeviceClass.PERIPHERAL_COMBO_KEYBOARD_POINTING_DEVICE),
            row(0b0101_0000_0000 + (1 shl 2), DeviceClass.PERIPHERAL_JOYSTICK),
            row(0b0101_0000_0000 + (7 shl 2), DeviceClass.PERIPHERAL_DIGITAL_PEN),
            row(0b0110_0000_0000, DeviceClass.IMAGING_UNCATEGORIZED),
            row(0b0110_0000_0000 + (1 shl 4), DeviceClass.IMAGING_DISPLAY),
            row(0b0110_0000_0000 + (1 shl 7), DeviceClass.IMAGING_PRINTER),
            row(Device.WEARABLE_WRIST_WATCH, DeviceClass.WEARABLE_WRISTWATCH),
            row(Device.WEARABLE_GLASSES, DeviceClass.WEARABLE_GLASSES),
            row(Device.TOY_ROBOT + 1, DeviceClass.TOY_ROBOT),
            row(Device.TOY_DOLL_ACTION_FIGURE, DeviceClass.TOY_DOLL_ACTION_FIGURE),
            row(Device.HEALTH_UNCATEGORIZED, DeviceClass.HEALTH_UNDEFINED),
            row(Device.HEALTH_BLOOD_PRESSURE, DeviceClass.HEALTH_BLOOD_PRESSURE_MONITOR),
            row(Device.HEALTH_DATA_DISPLAY, DeviceClass.HEALTH_HEALTH_DATA_DISPLAY),
            row(31 shl 8, DeviceClass.UNCATEGORIZED_UNCATEGORIZED)
        ).forAll { bluetoothClass, expected ->
            given("DeviceClass - Android의 BluetoothClass를 DeviceClass로 변환하기.") {
                logger.info { "[GIVEN] bluetoothClass=$bluetoothClass(0x${bluetoothClass.toString(16)}), expected=$expected" }

                val bleClass = mockk<BluetoothClass>()
                every { bleClass.majorDeviceClass } returns (bluetoothClass and 0x1F00)
                every { bleClass.deviceClass } returns (bluetoothClass and 0x1FFC)
                logger.info { "[GIVEN] bleClass=$bleClass" }

                `when`("변환하면") {
                    val actual = DeviceClass(bleClass)
                    logger.info { "[WHEN] actual=$actual" }

                    then("예상값과 같아야 한다.") {
                        actual shouldBe expected
                    }
                }
            }
        }
    }
}
