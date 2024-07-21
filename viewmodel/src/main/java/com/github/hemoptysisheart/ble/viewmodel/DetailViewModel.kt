package com.github.hemoptysisheart.ble.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.github.hemoptysisheart.ble.model.DeviceCacheModel
import com.github.hemoptysisheart.ble.ui.navigator.DetailNavigator.Companion.ARG_ADDRESS
import com.github.hemoptysisheart.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val deviceCacheModel: DeviceCacheModel
) : BaseViewModel("DetailViewModel") {
    private val address = saveStateHandle.get<String>(ARG_ADDRESS)
        ?: throw IllegalArgumentException("address is required")

    val device = deviceCacheModel[address]
        ?: throw IllegalArgumentException("device not found : address=$address")

    /**
     * 기기 연결하기.
     */
    fun onClickConnect() {
        Log.d(tag, "#onClickConnect called.")

        device.connect()
    }

    /**
     * 기기 연결 끊기.
     */
    fun onClickDisconnect() {
        Log.d(tag, "#onClickDisconnect called.")

        device.disconnect()
    }

    override fun toString() = listOf(
        super.toString(),
        "address=$address"
    ).joinToString(", ", "$tag(", ")")
}
