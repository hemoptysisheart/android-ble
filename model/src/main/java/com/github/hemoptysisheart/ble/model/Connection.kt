package com.github.hemoptysisheart.ble.model

import androidx.annotation.RequiresPermission
import com.github.hemoptysisheart.ble.domain.Connection
import com.github.hemoptysisheart.ble.domain.Connection.Level
import com.github.hemoptysisheart.ble.domain.Connection.State
import com.github.hemoptysisheart.ble.domain.Service
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Connection(
    val key: String,
    private val gatt: GattWrapper
) : Connection {
    /**
     * 로그용 태그.
     */
    private val tag: String = "Connection/$key"

    private val _state = MutableStateFlow(State(Level.DISCONNECTED))
    override val state: StateFlow<State> = _state

    override val level: Level
        get() = gatt.level

    override val mtu: Int?
        get() = gatt.mtu

    override val services: List<Service>
        get() = gatt.services ?: emptyList()

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override suspend fun requestMtu(mtu: Int) = gatt.requestMtu(mtu)

    /**
     * 서비스 목록을 갱신하고 반환한다.
     */
    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override suspend fun services(): List<Service> = gatt.discoverServices()

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override fun disconnect() = gatt.disconnect()

    override fun toString() = listOf(
        "level=$level",
        "mtu=$mtu",
        "services=$services"
    ).joinToString(", ", "$tag(", ")")
}
