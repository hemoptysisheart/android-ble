package com.github.hemoptysisheart.ble.model

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import com.github.hemoptysisheart.ble.domain.AbstractConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Connection(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val builder: (BluetoothGattCallback) -> BluetoothGatt
) : AbstractConnection<Device>("Connection") {
    override fun toString() = listOf(
        "state=${state.value}"
    ).joinToString(", ", "$tag(", ")")
}
