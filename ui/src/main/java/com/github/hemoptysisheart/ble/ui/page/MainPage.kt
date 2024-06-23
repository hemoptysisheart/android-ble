package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.hemoptysisheart.ble.ui.atom.AndroidBleTheme
import com.github.hemoptysisheart.ble.ui.navigator.MainNavigator
import com.github.hemoptysisheart.ble.viewmodel.MainViewModel
import com.github.hemoptysisheart.ui.compose.preview.PreviewPage
import com.github.hemoptysisheart.ui.navigation.compose.baseNavigator

@Composable
fun MainPage(
    navigator: MainNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    Log.v(TAG, "#MainPage args : navigator=$navigator, viewModel=$viewModel")

    MainPageContent(navigator)
}

@Composable
internal fun MainPageContent(
    navigator: MainNavigator
) {
    Log.v(TAG, "#MainPageContent args : navigator=$navigator")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "주변의 Bluetooth 기기 검색",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Button(onClick = navigator::scan, modifier = Modifier.padding(8.dp)) {
            Text("검색", modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.onPrimary)
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
@PreviewPage
internal fun PreviewMainPageContent() {
    AndroidBleTheme {
        MainPageContent(MainNavigator(baseNavigator()))
    }
}
