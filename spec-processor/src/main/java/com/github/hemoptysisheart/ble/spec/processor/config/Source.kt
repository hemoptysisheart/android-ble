package com.github.hemoptysisheart.ble.spec.processor.config

import java.io.File

/**
 * [`bluetooth-SIG/public`](https://bitbucket.org/bluetooth-SIG/public) 파일에 접근할 수 있는 정보.
 */
data class Source(
    /**
     * 로컬 레포지토리.
     */
    val path: File
) {
    init {
        require(path.isDirectory) { "path must be directory : path=${path.absolutePath}" }
    }
}
