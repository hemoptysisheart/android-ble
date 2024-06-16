package com.github.hemoptysisheart.ble.viewmodel

import androidx.lifecycle.LifecycleOwner
import com.github.hemoptysisheart.ble.model.ScanModel
import com.github.hemoptysisheart.ble.ui.state.RequiredPermission
import com.github.hemoptysisheart.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val scanModel: ScanModel
) : BaseViewModel("SplashViewModel") {
    companion object {
        const val TIMEOUT = 5_000L
    }

    private val _progress = MutableStateFlow(0F)
    val progress: StateFlow<Float> = _progress

    private val _timeout = MutableStateFlow(false)
    val timeout: StateFlow<Boolean> = _timeout

    private val _requiredPermission = MutableStateFlow<RequiredPermission?>(null)
    val requiredPermission: StateFlow<RequiredPermission?> = _requiredPermission

    override fun doOnCreate(owner: LifecycleOwner) {
        launch {
            var passed = 0L
            while (passed < TIMEOUT) {
                delay(10L)
                passed += 10L
                _progress.emit(passed.toFloat() / TIMEOUT)
            }
            _timeout.emit(true)
        }

        launch {
            if (!scanModel.granted) {
                _requiredPermission.emit(RequiredPermission.BLUETOOTH_SCAN)
            }
        }
    }
}
