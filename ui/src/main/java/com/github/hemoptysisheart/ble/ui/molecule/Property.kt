package com.github.hemoptysisheart.ble.ui.molecule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.hemoptysisheart.ble.ui.R
import com.github.hemoptysisheart.ui.compose.Text
import com.github.hemoptysisheart.ui.compose.preview.PreviewComponent
import com.github.hemoptysisheart.ui.state.SingleLine
import com.github.hemoptysisheart.ui.state.TextState

@Composable
fun Property(
    name: TextState,
    value: TextState,
    modifier: Modifier = Modifier,
    delimiter: Dp = 8.dp
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(state = name)
        Spacer(modifier = Modifier.width(delimiter))
        Text(state = value)
    }
}

@Composable
fun PropertyLarge(
    name: String,
    value: Any?,
    modifier: Modifier = Modifier
) {
    Property(
        name = TextState(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelLarge,
            textLines = SingleLine
        ),
        value = TextState(
            text = value?.toString() ?: stringResource(R.string.molecule_property_value_null),
            color = if (null == value) {
                MaterialTheme.colorScheme.outlineVariant
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            style = MaterialTheme.typography.bodyMedium
        ),
        modifier = modifier,
        delimiter = 16.dp
    )
}

@Composable
fun PropertyMedium(
    name: String,
    value: Any?,
    modifier: Modifier = Modifier
) {
    Property(
        name = TextState(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium,
            textLines = SingleLine
        ),
        value = TextState(
            text = value?.toString() ?: stringResource(R.string.molecule_property_value_null),
            color = if (null == value) {
                MaterialTheme.colorScheme.outlineVariant
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            style = MaterialTheme.typography.bodySmall
        ),
        modifier = modifier
    )
}

@Composable
fun PropertySmall(
    name: String,
    value: Any?,
    modifier: Modifier = Modifier
) {
    Property(
        name = TextState(
            text = name,
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.labelSmall,
            textLines = SingleLine
        ),
        value = TextState(
            text = value?.toString() ?: stringResource(R.string.molecule_property_value_null),
            color = if (null == value) {
                MaterialTheme.colorScheme.outlineVariant
            } else {
                MaterialTheme.colorScheme.outline
            },
            style = MaterialTheme.typography.labelSmall
        ),
        modifier = modifier,
        delimiter = 8.dp
    )
}

@Composable
@PreviewComponent
internal fun PropertyPreview() {
    Column {
        PropertyLarge(
            name = "NAME",
            value = "value",
            modifier = Modifier.padding(16.dp)
        )

        PropertyLarge(
            name = "NAME",
            value = null,
            modifier = Modifier.padding(16.dp)
        )

        PropertyMedium(
            name = "NAME",
            value = "value",
            modifier = Modifier.padding(16.dp)
        )

        PropertyMedium(
            name = "NAME",
            value = null,
            modifier = Modifier.padding(16.dp)
        )

        PropertySmall(
            name = "NAME",
            value = "value",
            modifier = Modifier.padding(16.dp)
        )

        PropertySmall(
            name = "NAME",
            value = null,
            modifier = Modifier.padding(16.dp)
        )
    }
}
