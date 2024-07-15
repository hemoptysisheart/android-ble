package com.github.hemoptysisheart.ble.domain

interface Characteristic {
    /**
     * [표준 캐릭터리스틱](https://www.bluetooth.com/specifications/assigned-numbers)에 정의된 캐릭터리스틱.
     * 없으면 [CustomCharacteristic]를 사용한다.
     */
    val type: com.github.hemoptysisheart.ble.spec.core.Characteristic

    /**
     * 읽기 가능 여부.
     */
    val readable: Boolean

    /**
     * 쓰기 가능 여부.
     */
    val writable: Boolean

    /**
     * 응답 없이 쓰기 가능 여부.
     */
    val writableWithoutResponse: Boolean

    val indicatable: Boolean

    val notifiable: Boolean

    val descriptors: List<Descriptor>

    suspend fun requestNotify()

    /**
     * 캐릭터리스틱의 값을 읽는다.
     *
     * @throws IllegalStateException 읽을 수 없는 캐릭터리스틱이거나 연결이 끊어진 경우.
     *
     * @see readable
     */
    @Throws(IllegalStateException::class)
    suspend fun read(): ByteArray
}
