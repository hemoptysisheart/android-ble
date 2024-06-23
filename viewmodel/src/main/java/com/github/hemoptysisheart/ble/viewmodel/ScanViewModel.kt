package com.github.hemoptysisheart.ble.viewmodel

import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleOwner
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.domain.defaultToString
import com.github.hemoptysisheart.ble.model.DeviceCacheModel
import com.github.hemoptysisheart.ble.model.ScanModel
import com.github.hemoptysisheart.ui.state.InteractionImpact
import com.github.hemoptysisheart.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val scanModel: ScanModel,
    private val deviceCacheModel: DeviceCacheModel
) : BaseViewModel("ScanViewModel") {
    private val _scanning = MutableStateFlow(false)
    val scanning: StateFlow<Boolean> = _scanning

    private val _devices = MutableStateFlow(listOf<Device>())
    val devices: StateFlow<List<Device>> = _devices

    @RequiresPermission("android.permission.BLUETOOTH_SCAN")
    fun onClickScan() {
        launch(InteractionImpact.VISIBLE) {
            _devices.update { emptyList() }
            _scanning.update { true }

            val deviceList = scanModel.scan()

            _devices.update { deviceList }
            _scanning.update { false }
        }
    }

    @RequiresPermission("android.permission.BLUETOOTH_SCAN")
    override fun doOnStart(owner: LifecycleOwner) {
        _devices.update { deviceCacheModel.devices }
    }

    override fun toString() = listOf(
        super.toString(),
        "scanModel=${scanModel.defaultToString()}",
        "deviceCacheModel=${deviceCacheModel.defaultToString()}",
        "scanning=${_scanning.value}",
        "devices=${_devices.value}"
    ).joinToString(", ", "$tag(", ")")
}
