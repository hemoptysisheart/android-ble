package com.github.hemoptysisheart.ble.spec

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothClass.Device
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UtilTest : BehaviorSpec() {
    private val logger = KotlinLogging.logger { }

    override fun isolationMode() = IsolationMode.InstancePerLeaf

    init {
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
