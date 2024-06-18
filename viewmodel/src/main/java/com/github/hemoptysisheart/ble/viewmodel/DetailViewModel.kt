package com.github.hemoptysisheart.ble.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.github.hemoptysisheart.ble.model.ConnectionModel
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator.Companion.ARG_ADDRESS
import com.github.hemoptysisheart.ble.ui.state.ConnectionState
import com.github.hemoptysisheart.ble.ui.state.ConnectionState.CONNECTED
import com.github.hemoptysisheart.ble.ui.state.ConnectionState.CONNECTING
import com.github.hemoptysisheart.ble.ui.state.ConnectionState.DISCONNECTED
import com.github.hemoptysisheart.ble.ui.state.ConnectionState.DISCONNECTING
import com.github.hemoptysisheart.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val connectionModel: ConnectionModel
) : BaseViewModel("DetailViewModel") {
    private val address = saveStateHandle.get<String>(ARG_ADDRESS)
        ?: throw IllegalArgumentException("address is required")

    private val _connection = MutableStateFlow(DISCONNECTED)
    val connection: StateFlow<ConnectionState> = _connection

    fun onClickConnect() {
        Log.d(tag, "#onClickConnect called.")

        launch {
            _connection.emit(CONNECTING)

            connectionModel.connect(address)

            _connection.emit(CONNECTED)
        }
    }

    fun onClickDisconnect() {
        Log.d(tag, "#onClickDisconnect called.")

        launch {
            _connection.emit(DISCONNECTING)
            delay(2000)
            _connection.emit(DISCONNECTED)
        }
    }

    override fun toString() = listOf(
        super.toString(),
        "address=$address",
    ).joinToString(", ", "$tag(", ")")
}
