package com.github.hemoptysisheart.ble.ui.organism

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.hemoptysisheart.ble.domain.Descriptor
import com.github.hemoptysisheart.ble.ui.R
import com.github.hemoptysisheart.ble.ui.molecule.PropertySmall
import com.github.hemoptysisheart.ble.ui.preview.DescriptorProvider
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent

@Composable
fun Descriptor(descriptor: Descriptor, modifier: Modifier = Modifier) {
    Log.v(TAG, "#Descriptor args : descriptor=$descriptor, modifier=$modifier")

    Column(modifier = modifier) {
        PropertySmall(
            name = stringResource(R.string.domain_descriptor_type_prop_name_label),
            value = descriptor.type.name
        )
        PropertySmall(
            name = stringResource(R.string.domain_descriptor_type_prop_uuid_label),
            value = descriptor.type.uuid
        )
        PropertySmall(name = stringResource(R.string.domain_descriptor_type_prop_id_label), value = descriptor.type.id)
    }
}

@Composable
@PreviewComponent
internal fun PreviewDescriptor(@PreviewParameter(DescriptorProvider::class) descriptor: Descriptor) {
    Descriptor(descriptor)
}
