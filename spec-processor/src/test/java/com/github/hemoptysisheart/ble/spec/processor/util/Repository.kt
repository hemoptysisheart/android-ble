package com.github.hemoptysisheart.ble.spec.processor.util

import java.io.File
import java.nio.file.Paths

val repositoryRootDir: File
    get() {
        val process = ProcessBuilder("git", "rev-parse", "--show-toplevel")
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        process.waitFor()

        return Paths.get(
            process.inputStream.bufferedReader().readText().trim(),
        ).toFile()
    }
