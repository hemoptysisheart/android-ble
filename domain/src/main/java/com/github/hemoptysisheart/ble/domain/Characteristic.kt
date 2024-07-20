package com.github.hemoptysisheart.ble.domain

interface Characteristic {
    val service: Service

    /**
     * [표준 캐릭터리스틱](https://www.bluetooth.com/specifications/assigned-numbers)에 정의된 캐릭터리스틱.
     * 없으면 [CustomCharacteristic]를 사용한다.
     */
    val type: com.github.hemoptysisheart.ble.spec.core.Characteristic

    val descriptors: List<Descriptor>
}
