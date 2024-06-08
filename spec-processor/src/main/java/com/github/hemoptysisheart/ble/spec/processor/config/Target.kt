package com.github.hemoptysisheart.ble.spec.processor.config

/**
 * [Source]의 정보로 만들 소스코드 정보.
 */
data class Target(
    /**
     * 생성할 소스코드의 푸트 패키지 이름.
     */
    val packageName: String = "com.github.hemoptysisheart.ble.spec"
)
