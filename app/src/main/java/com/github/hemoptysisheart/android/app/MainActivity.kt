package com.github.hemoptysisheart.android.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.hemoptysisheart.android.model.SampleModel
import com.github.hemoptysisheart.android.ui.RootUI
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    @Inject
    lateinit var sampleModel: SampleModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "#onCreate args : savedInstanceState=$savedInstanceState")
        super.onCreate(savedInstanceState)

        Log.d(TAG, "#onCreate : sampleModel=$sampleModel")

        enableEdgeToEdge()
        setContent {
            RootUI()
        }
    }
}
