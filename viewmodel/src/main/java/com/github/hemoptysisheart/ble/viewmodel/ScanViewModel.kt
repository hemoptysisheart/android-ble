package com.github.hemoptysisheart.ble.viewmodel

import com.github.hemoptysisheart.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor() : BaseViewModel("ScanViewModel") {
    private val _scanning = MutableStateFlow(false)
    val scanning: StateFlow<Boolean> = _scanning

    fun onClickScan() {
        launch {
            _scanning.emit(true)

            delay(5_000L)

            _scanning.emit(false)
        }
    }

    override fun toString() = listOf(
        super.toString()
    ).joinToString(", ", "$tag(", ")")
}
