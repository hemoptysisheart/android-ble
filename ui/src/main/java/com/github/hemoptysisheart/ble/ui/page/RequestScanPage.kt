package com.github.hemoptysisheart.ble.ui.page

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.hemoptysisheart.ble.ui.navigator.RequestScanNavigator

@Composable
fun RequestScanPage(
    navigator: RequestScanNavigator
) {
    Log.v(TAG, "#RequestScanPage args : navigator=$navigator")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "request bluetooth device scan.")
    }
}
