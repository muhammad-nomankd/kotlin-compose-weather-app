package com.felippeneves.kotlin_compose_weather_app.presentation.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felippeneves.kotlin_compose_weather_app.R
import com.felippeneves.kotlin_compose_weather_app.core.utils.DateUtil
import com.felippeneves.kotlin_compose_weather_app.core.utils.TemperatureFormatter
import com.felippeneves.kotlin_compose_weather_app.presentation.components.CityNotFoundComp
import com.felippeneves.kotlin_compose_weather_app.presentation.components.CustomToast
import com.felippeneves.kotlin_compose_weather_app.presentation.components.CustomTopAppBar
import com.felippeneves.kotlin_compose_weather_app.presentation.components.LoadingIndicator
import com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components.DailyWeatherItem
import com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components.HumidityWindPressureRow
import com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components.SunsetSunriseRow
import com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components.WeatherStatusWidget
import com.felippeneves.kotlin_compose_weather_app.presentation.features.home.events.HomeEvent
import com.felippeneves.kotlin_compose_weather_app.presentation.features.home.state.HomeState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun HomeContent(
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit,
    toastEvent: SharedFlow<String>
) {
    val isImperial = TemperatureFormatter.isImperial(uiState.measurementUnit)
    val weather = uiState.weather
    var toastMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        toastEvent.collect { message -> toastMessage = message }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = uiState.currentCity.name,
                showBackButton = false,
                showSearchButton = true,
                showFavoriteButton = true,
                isFavorite = uiState.currentCity.isFavorite,
                onFavoriteClick = { onEvent(HomeEvent.CheckedFavorite) }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            when {
                uiState.isLoading -> LoadingIndicator()

                weather == null -> CityNotFoundComp(errorMessage = uiState.errorMessage) {
                    onEvent(HomeEvent.RetryFetch)
                }

                else -> {
                    val currentWeather = weather.weeklyWeatherList.firstOrNull() ?: return@Surface

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Text(
                                modifier = Modifier.padding(bottom = 2.dp),
                                text = DateUtil.formatDate(currentWeather.timestampDate),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        item {
                            WeatherStatusWidget(weather = currentWeather, isImperial = isImperial)
                        }

                        item {
                            HumidityWindPressureRow(weather = currentWeather, isImperial = isImperial)
                        }

                        item {
                            SunsetSunriseRow(weather = currentWeather)
                        }

                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 4.dp),
                                text = stringResource(R.string.this_week),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        items(items = weather.weeklyWeatherList) { item ->
                            DailyWeatherItem(weather = item, isImperial = isImperial)
                        }
                    }
                }
            }

            toastMessage?.let { message ->
                CustomToast(message) { toastMessage = null }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    HomeContent(
        uiState = HomeState.createToPreview(),
        onEvent = {},
        toastEvent = MutableSharedFlow()
    )
}

@Preview
@Composable
private fun EmptyPreview() {
    HomeContent(
        uiState = HomeState(),
        onEvent = {},
        toastEvent = MutableSharedFlow()
    )
}
