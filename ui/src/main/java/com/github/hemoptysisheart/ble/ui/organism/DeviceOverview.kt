package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Device
import com.github.hemoptysisheart.ble.ui.R
import com.github.hemoptysisheart.ble.ui.molecule.PropertyLarge
import com.github.hemoptysisheart.ble.ui.molecule.PropertyMedium
import com.github.hemoptysisheart.ble.ui.preview.DeviceStateProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun DeviceOverview(device: Device.State, modifier: Modifier = Modifier) {
    Log.v(TAG, "#DeviceOverview args : device=$device")

    Column(modifier = modifier) {
        PropertyLarge(name = stringResource(R.string.domain_device_prop_name_label), value = device.name)
        PropertyMedium(name = stringResource(R.string.domain_device_prop_category_label), value = device.category)
        PropertyMedium(name = stringResource(R.string.domain_device_prop_services_label), value = device.services)
        PropertyMedium(name = stringResource(R.string.domain_device_prop_address_label), value = device.address)
    }
}

@Composable
@PreviewComponent
fun PreviewDeviceOverview(@PreviewParameter(DeviceStateProvider::class) device: Device.State) {
    DeviceOverview(device = device)
}
