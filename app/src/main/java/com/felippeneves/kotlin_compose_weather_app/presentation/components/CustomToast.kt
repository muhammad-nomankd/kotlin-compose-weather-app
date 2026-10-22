package com.felippeneves.kotlin_compose_weather_app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CustomToast(message: String, onDismiss: () -> Unit) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        isVisible = false
        onDismiss()
    }

    if (isVisible) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.inverseSurface,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CustomToast(message = "This is a custom toast", onDismiss = {})
}
