package com.felippeneves.kotlin_compose_weather_app.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchInput(
    modifier: Modifier = Modifier,
    title: String,
    query: String,
    onTextChanged: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp),
        value = query,
        onValueChange = onTextChanged,
        label = { Text(text = title) },
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onTextChanged("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search"
                        )
                    }
                }
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search icon"
                )
            }
        },
        maxLines = 1,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.outline
        )
    )
}

@Preview
@Composable
private fun Preview() {
    SearchInput(
        query = "São Paulo",
        title = "Search",
        onTextChanged = {}
    )
}
