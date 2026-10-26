package com.felippeneves.kotlin_compose_weather_app.presentation.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
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
import com.felippeneves.kotlin_compose_weather_app.core.utils.TemperatureFormatter
import com.felippeneves.kotlin_compose_weather_app.presentation.components.CityNotFoundComp
import com.felippeneves.kotlin_compose_weather_app.presentation.components.CustomToast
import com.felippeneves.kotlin_compose_weather_app.presentation.components.CustomTopAppBar
import com.felippeneves.kotlin_compose_weather_app.presentation.components.LoadingIndicator
import com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components.DailyWeatherItem
import com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components.HumidityWindPressureRow
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

    val countryName = weather?.city?.country ?: ""
    val locationSubtitle = if (countryName.isNotEmpty()) "Khyber Pakhtunkhwa, $countryName" else ""

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = uiState.currentCity.name,
                subtitle = locationSubtitle,
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
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            WeatherStatusWidget(weather = currentWeather, isImperial = isImperial)
                        }

                        item {
                            HumidityWindPressureRow(weather = currentWeather, isImperial = isImperial)
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp, bottom = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Calendar",
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = stringResource(R.string.this_week),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }

                                Text(
                                    text = "7 Days >",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        itemsIndexed(items = weather.weeklyWeatherList) { index, item ->
                            DailyWeatherItem(
                                weather = item,
                                isImperial = isImperial,
                                isToday = (index == 0)
                            )
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
