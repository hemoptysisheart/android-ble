package com.github.hemoptysisheart.ble.app

import android.util.Log
import com.github.hemoptysisheart.statepump.ScaffoldPump
import com.github.hemoptysisheart.statepump.ScaffoldPumpImpl
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
    fun provideScaffoldPump(): ScaffoldPump {
        val pump = ScaffoldPumpImpl()
        Log.i(TAG, "#provideScaffoldPump return : pump=$pump")
        return pump
    }
}
