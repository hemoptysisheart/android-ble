package com.github.hemoptysisheart.ble.spec.annotation

/**
 * 스팩 코드를 생성할 대상 패키지를 지정합니다.
 *
 * 이 어노테이션을 가진 클래스가 있는 패키지를 기준으로 스펙 코드를 생성한다.
 */
@Deprecated("https://github.com/hemoptysisheart/android-ble/issues/4")
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class TargetPackage
