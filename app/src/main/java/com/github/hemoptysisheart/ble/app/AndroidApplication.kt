package com.github.hemoptysisheart.ble.app

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidApplication : Application() {
    companion object {
        private const val TAG = "AndroidApplication"
    }

    override fun onCreate() {
        Log.i(TAG, "#onCreate called.")
        super.onCreate()
    }

    override fun onTerminate() {
        Log.i(TAG, "#onTerminate called.")
        super.onTerminate()
    }
}
