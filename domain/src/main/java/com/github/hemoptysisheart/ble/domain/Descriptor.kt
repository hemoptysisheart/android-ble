package com.github.hemoptysisheart.ble.domain

import java.util.UUID

interface Descriptor {
    companion object {
        /**
         * 캐릭터리스틱을 설정하는데 필요한 [Client Characteristic Configuration](https://www.bluetooth.com/wp-content/uploads/Files/Specification/HTML/Core-54/out/en/host/generic-attribute-profile--gatt-.html#UUID-58fcda17-4f4b-3f53-3ca8-077bbfc77c5d) 디스크립터의 UUID.
         */
        val UUID_CLIENT_CHARACTERISTIC_CONFIGURATION = UUID.fromString("00002902-0000-1000-8000-00805F9B34FB")
    }

    val characteristic: Characteristic

    /**
     * 디스크립터 종류.
     *
     * 참조 :
     * - [Assigned Numbers](https://www.bluetooth.com/specifications/assigned-numbers)
     */
    val type: com.github.hemoptysisheart.ble.spec.core.Descriptor
}
