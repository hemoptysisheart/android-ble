package com.github.hemoptysisheart.android.model

import android.util.Log

@Deprecated("This is a sample model.")
class SampleModel {
    companion object {
        private const val TAG = "SampleModel"
    }

    init {
        Log.d(TAG, "#init called.")
    }
}
