package com.github.hemoptysisheart.ble.viewmodel

import androidx.lifecycle.LifecycleOwner
import com.github.hemoptysisheart.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel("SplashViewModel") {
    companion object {
        const val TIMEOUT = 5_000L
    }

    private var createdAt: Long = Long.MIN_VALUE
    private var timeoutAt: Long = Long.MIN_VALUE

    private val _progress = MutableStateFlow(0.0F)
    val progress: StateFlow<Float> = _progress

    override fun doOnCreate(owner: LifecycleOwner) {
        createdAt = Instant.now().toEpochMilli()
        timeoutAt = createdAt + TIMEOUT

        launch {
            val now = Instant.now().toEpochMilli()
            while (now < timeoutAt) {
                _progress.emit((now - createdAt).toFloat() / TIMEOUT)
                delay(100L)
            }
        }
    }
}
