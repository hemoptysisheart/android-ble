package com.github.hemoptysisheart.ble.viewmodel

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

    override fun toString() = listOf(
        super.toString(),
        "address=$address",
        "device=$device"
    ).joinToString(", ", "$tag(", ")")
}
