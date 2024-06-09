package com.github.hemoptysisheart.ble.viewmodel

import androidx.lifecycle.LifecycleOwner
import com.github.hemoptysisheart.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel("SplashViewModel") {
    companion object {
        const val TIMEOUT = 5_000L
    }

    private val _progress = MutableStateFlow(0F)
    val progress: StateFlow<Float> = _progress

    private val _timeout = MutableStateFlow(false)
    val timeout: StateFlow<Boolean> = _timeout

    override fun doOnCreate(owner: LifecycleOwner) {
        launch {
            var passed = 0L
            while (passed < TIMEOUT) {
                delay(100L)
                passed += 100L
                _progress.emit(passed.toFloat() / TIMEOUT)
            }
            _timeout.emit(true)
        }
    }
}
