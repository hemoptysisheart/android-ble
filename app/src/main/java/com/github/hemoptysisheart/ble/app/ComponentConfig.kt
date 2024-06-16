package com.github.hemoptysisheart.ble.app

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.util.Log
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
    fun provideScanModel(@ApplicationContext context: Context, permissionModel: PermissionModel): ScanModel {
        val model = ScanModelImpl(
            permissionModel = permissionModel,
            scanner = (context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter.bluetoothLeScanner
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
