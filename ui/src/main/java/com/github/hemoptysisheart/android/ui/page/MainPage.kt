package com.github.hemoptysisheart.android.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.hemoptysisheart.android.ui.atom.AndroidTemplateTheme
import com.github.hemoptysisheart.android.viewmodel.MainViewModel
import java.time.Instant

@Composable
fun MainPage(
    viewModel: MainViewModel = hiltViewModel()
) {
    Log.v(TAG, "#MainPage args : viewModel=$viewModel")

    val clock by viewModel.clock.collectAsStateWithLifecycle()

    MainPageContent(clock)
}

@Composable
private fun MainPageContent(
    clock: Instant
) {
    Log.v(TAG, "#MainPageContent args : clock=$clock")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = clock.toString(), color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
@PreviewDynamicColors
@PreviewFontScale
@PreviewLightDark
@PreviewScreenSizes
fun MainPagePreview() {
    AndroidTemplateTheme {
        MainPageContent(Instant.now())
    }
}
