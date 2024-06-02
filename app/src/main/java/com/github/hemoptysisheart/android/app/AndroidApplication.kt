package com.github.hemoptysisheart.android.app

import android.app.Application
import android.util.Log
import com.github.hemoptysisheart.android.model.SampleModel
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AndroidApplication : Application() {
    companion object {
        private const val TAG = "AndroidApplication"
    }

    @Deprecated("This is a sample model.")
    @Inject
    lateinit var sampleModel: SampleModel

    override fun onCreate() {
        Log.i(TAG, "#onCreate called.")
        super.onCreate()

        Log.d(TAG, "#onCreate : sampleModel=$sampleModel")
    }

    override fun onTerminate() {
        Log.i(TAG, "#onTerminate called.")
        super.onTerminate()
    }
}
