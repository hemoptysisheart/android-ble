package com.github.hemoptysisheart.ble.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.ble.ui.navigator.SplashNavigator
import com.github.hemoptysisheart.ble.viewmodel.SplashViewModel
import com.github.hemoptysisheart.ui.navigation.compose.baseNavigator
import com.github.hemoptysisheart.ui.navigation.compose.baseViewModel

@Composable
fun SplashPage(
    navigator: SplashNavigator,
    viewModel: SplashViewModel = baseViewModel()
) {
    val progress by viewModel.progress.collectAsStateWithLifecycle()

    SplashPageContent(
        navigator = navigator,
        progress = progress
    )
}

@Composable
internal fun SplashPageContent(
    navigator: SplashNavigator,
    progress: Float,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1F))
        Text(text = "SPLASH")
        Spacer(modifier = Modifier.weight(1F))
        LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
    }
}

internal data class SplashParam(
    val progress: Float
)

internal class SplashParamProvider : PreviewParameterProvider<SplashParam> {
    override val values = sequenceOf(
        SplashParam(progress = 0.0F),
        SplashParam(progress = 0.5F),
        SplashParam(progress = 1.0F)
    )
}

@Composable
@PreviewLightDark
internal fun SplashPagePreview(
    @PreviewParameter(SplashParamProvider::class) param: SplashParam
) {
    SplashPageContent(
        navigator = SplashNavigator(baseNavigator()),
        progress = param.progress
    )
}
