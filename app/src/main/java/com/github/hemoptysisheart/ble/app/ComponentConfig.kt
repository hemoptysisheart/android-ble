package com.github.hemoptysisheart.ble.app

import android.content.Context
import android.util.Log
import com.github.hemoptysisheart.ble.model.DeviceCacheModel
import com.github.hemoptysisheart.ble.model.DeviceCacheModelImpl
import com.github.hemoptysisheart.ble.model.PermissionModel
import com.github.hemoptysisheart.ble.model.PermissionModelImpl
import com.github.hemoptysisheart.ble.model.ScanModel
import com.github.hemoptysisheart.ble.model.ScanModelImpl
import com.github.hemoptysisheart.statepump.ScaffoldPump
import com.github.hemoptysisheart.statepump.ScaffoldPumpImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providePermissionModel(
        @ApplicationContext context: Context
    ): PermissionModel {
        val model = PermissionModelImpl(context)
        Log.i(TAG, "#providePermissionModel return : model=$model")
        return model
    }

    @Provides
    @Singleton
    fun provideDeviceCacheModel(): DeviceCacheModel {
        val model = DeviceCacheModelImpl()
        Log.i(TAG, "#provideDeviceCacheModel return : model=$model")
        return model
    }

    @Provides
    @Singleton
    fun provideScanModel(
        @ApplicationContext context: Context,
        permissionModel: PermissionModel,
        deviceCacheModel: DeviceCacheModel
    ): ScanModel {
        val model = ScanModelImpl(
            context = context,
            permissionModel = permissionModel,
            deviceCacheModel = deviceCacheModel
        )
        Log.i(TAG, "#provideScanModel return : model=$model")
        return model
    }

    @Provides
    @Singleton
    fun provideScaffoldPump(): ScaffoldPump {
        val pump = ScaffoldPumpImpl()
        Log.i(TAG, "#provideScaffoldPump return : pump=$pump")
        return pump
    }
}
