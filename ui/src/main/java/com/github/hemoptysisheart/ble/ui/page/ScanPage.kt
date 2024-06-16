package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.ScanNavigator
import com.github.hemoptysisheart.ble.viewmodel.ScanViewModel
import com.github.hemoptysisheart.ui.navigation.compose.baseNavigator

@Composable
fun ScanPage(navigator: ScanNavigator, viewModel: ScanViewModel = hiltViewModel()) {
    Log.v(TAG, "#ScanPage args : navigator=$navigator")

    val scanning by viewModel.scanning.collectAsStateWithLifecycle()

    ScanPageContent(
        navigator = navigator,
        scanning = scanning,
        onClickScan = viewModel::onClickScan
    )
}

@Composable
internal fun ScanPageContent(
    navigator: ScanNavigator,
    scanning: Boolean,
    onClickScan: () -> Unit = {}
) {
    Log.v(TAG, "#ScanPageContent args : navigator=$navigator")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(Modifier.weight(1F)) {
        }
        Button(onClick = onClickScan, modifier = Modifier.padding(8.dp), enabled = !scanning) {
            Text(text = "검색", modifier = Modifier.padding(8.dp))
        }
    }
}

internal data class ScanPageParam(
    val scanning: Boolean
)

internal class ScanPageParamProvider : PreviewParameterProvider<ScanPageParam> {
    override val values = sequenceOf(
        ScanPageParam(false),
        ScanPageParam(true)
    )
}

@Composable
@PreviewLightDark
@PreviewFontScale
internal fun ScanPagePreview(@PreviewParameter(ScanPageParamProvider::class) param: ScanPageParam) {
    AndroidBleTheme {
        ScanPageContent(ScanNavigator(baseNavigator()), param.scanning)
    }
}
