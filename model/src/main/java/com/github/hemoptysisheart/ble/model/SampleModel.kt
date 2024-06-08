package com.github.hemoptysisheart.ble.model

import android.util.Log
import com.github.hemoptysisheart.ble.spec.MajorDeviceClass

@Deprecated("This is a sample model.")
class SampleModel {
    companion object {
        private const val TAG = "SampleModel"
    }

    init {
        Log.d(TAG, "#init called.")
        Log.e(TAG, "init : MajorDeviceClass.entries=${MajorDeviceClass.entries}")
    }
}
