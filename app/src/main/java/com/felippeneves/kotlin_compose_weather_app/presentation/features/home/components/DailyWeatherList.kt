package com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felippeneves.kotlin_compose_weather_app.domain.model.WeatherItem

@Composable
fun DailyWeatherList(
    modifier: Modifier = Modifier,
    data: List<WeatherItem>,
    isImperial: Boolean
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        data.forEach { item ->
            DailyWeatherItem(weather = item, isImperial = isImperial)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DailyWeatherList(
        data = WeatherItem.createListToPreview(),
        isImperial = false
    )
}
