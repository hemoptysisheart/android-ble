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

    /**
     * Notify (알림)
     * - 서버가 클라이언트에게 데이터를 전송할 때 사용합니다.
     * - 클라이언트로부터의 응답(acknowledgment)을 요구하지 않습니다.
     * - 데이터 전송이 덜 신뢰성 있을 수 있으며, 데이터가 손실될 가능성이 있습니다.
     * - 배터리 사용량이나 리소스 소모 측면에서 더 효율적일 수 있습니다.
     */
    suspend fun requestNotify()

    /**
     * Indicate (지시)
     * - 서버가 클라이언트에게 데이터를 전송할 때 사용합니다.
     * - 클라이언트는 데이터를 수신한 후 응답(acknowledgment)을 보내야 합니다.
     * - 데이터 전송이 더 신뢰성 있으며, 클라이언트가 데이터를 수신했는지 확인할 수 있습니다.
     * - Notify에 비해 배터리 사용량이나 리소스 소모가 더 클 수 있습니다.
     */
    suspend fun requestIndicate()

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
