package com.github.hemoptysisheart.ble.app

import android.util.Log
import com.github.hemoptysisheart.ble.model.SampleModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ComponentConfig {
    companion object {
        private const val TAG = "ComponentConfig"
    }

    @Provides
    @Singleton
    fun provideSampleModel(): SampleModel {
        val model = SampleModel()
        Log.d(TAG, "#provideSampleModel return : model=$model")
        return model
    }
}
