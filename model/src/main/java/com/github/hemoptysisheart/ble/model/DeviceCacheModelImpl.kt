package com.github.hemoptysisheart.ble.model

import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.model.DeviceCacheModel.Companion.TTL_DEFAULT
import com.github.hemoptysisheart.ble.model.DeviceCacheModel.Companion.TTL_MAX
import com.github.hemoptysisheart.ble.model.DeviceCacheModel.Companion.TTL_MIN
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant

class DeviceCacheModelImpl(
    override val ttl: Duration = Duration.ofMillis(TTL_DEFAULT),
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : DeviceCacheModel {
    companion object {
        private const val TAG = "DeviceCacheModelImpl"
    }

    private var _devices: MutableMap<String, Device>? = null
    private var expireJob: Job? = null

    override var expireAt: Instant = Instant.MIN
        private set

    override val devices: List<Device>
        get() = _devices?.values?.toList()
            ?: emptyList()

    override val cached: Boolean
        get() = null != _devices

    init {
        require(ttl.toMillis() in TTL_MIN..TTL_MAX) { "ttl must be in range $TTL_MIN..$TTL_MAX, but $ttl." }
    }

    override fun cache(devices: List<Device>) {
        _devices = devices.associateBy { it.address }.toMutableMap()
        // TODO 테스트 가능한 시각 설정 방법 추가하기.
        expireAt = Instant.now() + ttl

        expireJob?.cancel()
        expireJob = CoroutineScope(dispatcher).launch {
            delay(ttl.toMillis())
            clear()
        }
    }

    override fun get(address: String): Device? = _devices?.get(address)

    override fun clear() {
        _devices = null
        expireAt = Instant.MIN
    }

    override fun toString() = listOf(
        "ttl=$ttl,",
        "expireAt=$expireAt,",
        "cached=$cached,",
        "devices=$devices",
    ).joinToString(", ", "$TAG(", ")")
}
