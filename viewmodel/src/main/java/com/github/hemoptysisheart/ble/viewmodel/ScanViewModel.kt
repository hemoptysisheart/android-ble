package com.github.hemoptysisheart.ble.viewmodel

import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleOwner
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.model.ScanModel
import com.github.hemoptysisheart.ui.state.InteractionImpact
import com.github.hemoptysisheart.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val scanModel: ScanModel
) : BaseViewModel("ScanViewModel") {
    private val _scanning = MutableStateFlow(false)
    val scanning: StateFlow<Boolean> = _scanning

    private val _devices = MutableStateFlow(listOf<Device>())
    val devices: StateFlow<List<Device>> = _devices

    @RequiresPermission("android.permission.BLUETOOTH_SCAN")
    fun onClickScan() {
        launch(InteractionImpact.VISIBLE) {
            _devices.emit(emptyList())
            _scanning.emit(true)
            val deviceList = scanModel.scan()
            _devices.emit(deviceList)
            _scanning.emit(false)
        }
    }

    @RequiresPermission("android.permission.BLUETOOTH_SCAN")
    override fun doOnStart(owner: LifecycleOwner) {
        if (_devices.value.isNotEmpty()) {
            onClickScan()
        }
    }

    override fun toString() = listOf(
        super.toString()
    ).joinToString(", ", "$tag(", ")")
}
