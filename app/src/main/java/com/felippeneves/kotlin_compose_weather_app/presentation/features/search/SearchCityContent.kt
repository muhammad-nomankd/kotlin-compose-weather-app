package com.felippeneves.kotlin_compose_weather_app.presentation.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felippeneves.kotlin_compose_weather_app.R
import com.felippeneves.kotlin_compose_weather_app.presentation.components.CustomToast
import com.felippeneves.kotlin_compose_weather_app.presentation.components.CustomTopAppBar
import com.felippeneves.kotlin_compose_weather_app.presentation.components.SearchInput
import com.felippeneves.kotlin_compose_weather_app.presentation.features.search.events.SearchCityEvent
import com.felippeneves.kotlin_compose_weather_app.presentation.features.search.state.SearchCityState
import com.felippeneves.kotlin_compose_weather_app.presentation.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun SearchCityContent(
    uiState: SearchCityState,
    onEvent: (SearchCityEvent) -> Unit,
    finishScreen: SharedFlow<Unit>,
    toastEvent: SharedFlow<String>
) {
    var toastMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        launch { toastEvent.collect { message -> toastMessage = message } }
        launch { finishScreen.collect { NavigationManager.navigateBack() } }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(title = stringResource(R.string.search))
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(20.dp),
            ) {
                SearchInput(
                    title = stringResource(R.string.city),
                    query = uiState.query,
                    onTextChanged = { onEvent(SearchCityEvent.UpdateQuery(it)) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    onClick = {
                        onEvent(SearchCityEvent.ConfirmCity)
                    },
                ) {
                    Text(
                        text = stringResource(R.string.search),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
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
    SearchCityContent(
        uiState = SearchCityState.createToPreview(),
        onEvent = {},
        finishScreen = MutableSharedFlow(),
        toastEvent = MutableSharedFlow()
    )
}
