package com.github.hemoptysisheart.ble.viewmodel

import androidx.lifecycle.LifecycleOwner
import com.github.hemoptysisheart.ble.model.ScanModel
import com.github.hemoptysisheart.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RequestScanViewModel @Inject constructor(
    private val scanModel: ScanModel
) : BaseViewModel("RequestScanViewModel") {
    val permission = scanModel.permission

    private val _granted = MutableStateFlow(false)
    val granted: StateFlow<Boolean> = _granted

    private val _permissionRequested = MutableStateFlow(false)
    val permissionRequested: StateFlow<Boolean> = _permissionRequested

    fun onClickRequestPermission(callback: () -> Unit) {
        launch {
            _permissionRequested.emit(true)
            callback()
        }
    }

    override fun doOnResume(owner: LifecycleOwner) {
        launch {
            _granted.emit(scanModel.granted)
        }
    }

    override fun toString() = listOf(
        super.toString(),
        "scanModel=$scanModel"
    ).joinToString(", ", "$tag(", ")")
}
