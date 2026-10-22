package com.felippeneves.kotlin_compose_weather_app.presentation.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.felippeneves.kotlin_compose_weather_app.presentation.features.search.events.SearchCityEvent
import com.felippeneves.kotlin_compose_weather_app.presentation.features.search.state.SearchCityState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SearchCityScreen(
	uiState: SearchCityState,
	onEvent: (SearchCityEvent) -> Unit,
	finishScreen: SharedFlow<Unit>,
	toastEvent: SharedFlow<String>
) {
        Text("hi")
		SearchCityContent(
			uiState = uiState,
			onEvent = onEvent,
			finishScreen = finishScreen,
			toastEvent = toastEvent
		)

}

@Preview
@Composable
private fun Preview() {
	SearchCityScreen(
		uiState = SearchCityState.createToPreview(),
		onEvent = {},
		finishScreen = MutableSharedFlow(),
		toastEvent = MutableSharedFlow()
	)
}
