package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.SplashNavigator
import com.github.hemoptysisheart.ble.ui.state.RequiredPermission
import com.github.hemoptysisheart.ble.viewmodel.SplashViewModel
import com.github.hemoptysisheart.ui.compose.preview.PreviewPage
import com.github.hemoptysisheart.ui.navigation.compose.baseNavigator
import com.github.hemoptysisheart.ui.navigation.compose.baseViewModel

@Composable
fun SplashPage(
    navigator: SplashNavigator,
    viewModel: SplashViewModel = baseViewModel()
) {
    val progress by viewModel.progress.collectAsStateWithLifecycle()
    val timeout by viewModel.timeout.collectAsStateWithLifecycle()
    val requiredPermission by viewModel.requiredPermission.collectAsStateWithLifecycle()

    var breakaway by remember {
        mutableStateOf(false)
    }

    if (timeout && !breakaway) {
        breakaway = true
        when (requiredPermission) {
            RequiredPermission.BLUETOOTH_SCAN ->
                navigator.requestScan()

            else ->
                navigator.main()
        }
    } else {
        SplashPageContent(
            navigator = navigator,
            progress = progress
        )
    }
}

@Composable
internal fun SplashPageContent(
    navigator: SplashNavigator,
    progress: Float
) {
    Log.v(TAG, "#SplashPageContent args : navigator=$navigator, progress=$progress")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1F))
        Text(text = "SPLASH", color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.weight(1F))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

internal data class SplashParam(
    val progress: Float
)

internal class SplashParamProvider : PreviewParameterProvider<SplashParam> {
    override val values = sequenceOf(
        SplashParam(progress = 0.0F),
        SplashParam(progress = 0.25F),
        SplashParam(progress = 0.5F),
        SplashParam(progress = 0.75F),
        SplashParam(progress = 1.0F)
    )
}

@Composable
@PreviewPage
internal fun PreviewSplashPageContent(
    @PreviewParameter(SplashParamProvider::class) param: SplashParam
) {
    AndroidBleTheme {
        SplashPageContent(
            navigator = SplashNavigator(baseNavigator()),
            progress = param.progress
        )
    }
}
