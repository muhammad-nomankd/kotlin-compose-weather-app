package com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.felippeneves.kotlin_compose_weather_app.domain.model.WeatherItem

@Composable
fun SunsetSunriseRow(
    modifier: Modifier = Modifier,
    @Suppress("UNUSED_PARAMETER") weather: WeatherItem
) {
    Spacer(modifier = modifier)
}
