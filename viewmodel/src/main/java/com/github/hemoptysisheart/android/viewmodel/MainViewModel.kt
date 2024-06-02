package com.github.hemoptysisheart.android.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.hemoptysisheart.android.model.SampleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sampleModel: SampleModel
) : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _clock = MutableStateFlow(Instant.now())
    val clock: StateFlow<Instant> = _clock

    private val clockJob: Job

    init {
        Log.d(TAG, "#init called.")

        clockJob = viewModelScope.launch {
            while (true) {
                _clock.value = Instant.now()
                delay(1_000L)
            }
        }
        Log.d(TAG, "#init : sampleModel=$sampleModel, clockJob=$clockJob")
    }

    override fun onCleared() {
        Log.d(TAG, "#onCleared called.")
        super.onCleared()

        clockJob.cancel()
    }

    override fun toString() = listOf(
        "sampleModel=$sampleModel",
        "clock=${clock.value}",
        "clockJob=$clockJob"
    ).joinToString(", ", "$TAG(", ")")
}
