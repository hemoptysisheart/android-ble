package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Service
import com.github.hemoptysisheart.ble.ui.R
import com.github.hemoptysisheart.ble.ui.molecule.PropertyMedium
import com.github.hemoptysisheart.ble.ui.molecule.PropertySmall
import com.github.hemoptysisheart.ble.ui.preview.ServiceProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun ServiceOverview(service: Service, modifier: Modifier = Modifier) {
    Log.v(TAG, "#ServiceOverview args : service=$service, modifier=$modifier")

    Column(modifier = modifier) {
        PropertyMedium(name = stringResource(R.string.domain_service_type_prop_name_label), value = service.type.name)
        PropertySmall(name = stringResource(R.string.domain_service_type_prop_uuid_label), value = service.type.uuid)
        PropertySmall(name = stringResource(R.string.domain_service_type_prop_id_label), value = service.type.id)
    }
}

@Composable
@PreviewComponent
fun PreviewServiceOverview(@PreviewParameter(ServiceProvider::class) service: Service) {
    ServiceOverview(service)
}
