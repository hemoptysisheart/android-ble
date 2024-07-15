package com.github.hemoptysisheart.ble.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.github.hemoptysisheart.ble.domain.Characteristic
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.model.ConnectionModel
import com.github.hemoptysisheart.ble.model.DeviceCacheModel
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator.Companion.ARG_ADDRESS
import com.github.hemoptysisheart.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val deviceCacheModel: DeviceCacheModel,
    private val connectionModel: ConnectionModel
) : BaseViewModel("DetailViewModel") {
    private val address = saveStateHandle.get<String>(ARG_ADDRESS)
        ?: throw IllegalArgumentException("address is required")

    val device = deviceCacheModel[address]
        ?: throw IllegalArgumentException("device not found : address=$address")

    private val _connection: Connection = connectionModel.build(address)
    val connection: StateFlow<Connection.State> = _connection.state

    /**
     * 기기 연결하기.
     */
    fun onClickConnect() {
        Log.d(tag, "#onClickConnect called.")

        launch {
            Log.d(tag, "#onClickConnect : connection=$connection")
            if (this@DetailViewModel.device !== _connection.device) {
                Log.w(
                    tag,
                    "#onClickConnect device does not match : this.device=${this@DetailViewModel.device}, connection.device=${_connection.device}"
                )
            }

            _connection.connect()
        }
    }

    /**
     * 기기 연결 끊기.
     */
    fun onClickDisconnect() {
        Log.d(tag, "#onClickDisconnect called.")

        launch {
            _connection.disconnect()
        }
    }

    fun onClickNotification(characteristic: Characteristic) {
        Log.d(tag, "#onClickNotification args : characteristic=$characteristic")
    }

    fun onClickRead(characteristic: Characteristic) {
        Log.d(tag, "#onClickRead args : characteristic=$characteristic")

        launch {
            characteristic.read()
        }
    }

    override fun toString() = listOf(
        super.toString(),
        "address=$address",
        "connection=$_connection"
    ).joinToString(", ", "$tag(", ")")
}
