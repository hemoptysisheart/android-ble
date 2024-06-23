package com.github.hemoptysisheart.ble.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.hemoptysisheart.ble.spec.core.MajorServiceClass
import kotlin.random.Random

class MajorServiceClassProvider : PreviewParameterProvider<MajorServiceClass> {
    override val values: Sequence<MajorServiceClass> = MajorServiceClass.entries.asSequence()
}

class MajorServiceClassListProvider : PreviewParameterProvider<List<MajorServiceClass>> {
    override val values: Sequence<List<MajorServiceClass>> = sequenceOf(
        emptyList(),
        listOf(MajorServiceClass.entries.random()),
        MajorServiceClass.entries.filter { Random.nextBoolean() },
        MajorServiceClass.entries.toList()
    )
}
