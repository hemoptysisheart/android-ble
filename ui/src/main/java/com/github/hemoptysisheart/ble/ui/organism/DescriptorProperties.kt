package com.github.hemoptysisheart.ble.ui.organism

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.github.hemoptysisheart.ble.domain.Descriptor
import com.github.hemoptysisheart.ble.ui.R

@Composable
fun DescriptorProperties(descriptor: Descriptor) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        descriptor.readable.let { readable ->
            InputChip(selected = readable, onClick = {}, label = {
                Text(
                    text = stringResource(R.string.domain_descriptor_readable_label, if (readable) "✔️ " else ""),
                    style = MaterialTheme.typography.labelSmall
                )
            })
        }

        descriptor.writable.let { writable ->
            InputChip(
                selected = writable,
                onClick = {},
                label = {
                    Text(
                        text = stringResource(R.string.domain_descriptor_writable_label, if (writable) "✔️ " else ""),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}
