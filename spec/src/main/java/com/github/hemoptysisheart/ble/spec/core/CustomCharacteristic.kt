package com.github.hemoptysisheart.ble.spec.core

import java.util.UUID

/**
 * [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers)에 등록되지 않은 기기 제조사에서 정의한 캐릭터리스틱.
 */
data class CustomCharacteristic(
    override val uuid: UUID,
    override val id: String? = null,
    override val name: String? = null
) : Characteristic
