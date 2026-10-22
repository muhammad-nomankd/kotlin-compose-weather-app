package com.felippeneves.kotlin_compose_weather_app.presentation.features.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felippeneves.kotlin_compose_weather_app.R
import com.felippeneves.kotlin_compose_weather_app.core.utils.TemperatureFormatter
import com.felippeneves.kotlin_compose_weather_app.presentation.components.CustomToast
import com.felippeneves.kotlin_compose_weather_app.presentation.components.CustomTopAppBar
import com.felippeneves.kotlin_compose_weather_app.presentation.components.ErrorComp
import com.felippeneves.kotlin_compose_weather_app.presentation.components.LoadingIndicator
import com.felippeneves.kotlin_compose_weather_app.presentation.features.settings.components.MeasurementUnitListDialog
import com.felippeneves.kotlin_compose_weather_app.presentation.features.settings.events.SettingsEvent
import com.felippeneves.kotlin_compose_weather_app.presentation.features.settings.state.SettingsState
import com.felippeneves.kotlin_compose_weather_app.presentation.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Composable
fun SettingsContent(
    uiState: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
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
            CustomTopAppBar(title = stringResource(R.string.settings))
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            when {
                uiState.errorMessage.isNotEmpty() -> ErrorComp(errorMessage = uiState.errorMessage) {
                    onEvent(SettingsEvent.RetryFetch)
                }

                uiState.isLoading -> LoadingIndicator()

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Preferences",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onEvent(SettingsEvent.ShowMeasurementUnitListDialog) },
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shadowElevation = 3.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                        modifier = Modifier.size(44.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Settings,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .size(24.dp)
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = stringResource(id = R.string.change_units),
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )

                                        Text(
                                            text = TemperatureFormatter.getUnitLabel(
                                                uiState.measurementUnit,
                                                LocalContext.current
                                            ),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "Change unit",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(28.dp))

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
                                onEvent(SettingsEvent.SaveMeasurementUnit)
                            },
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = null
                                )
                                Text(
                                    text = stringResource(R.string.save),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            uiState.showMeasurementUnitListDialog.takeIf { it }?.let {
                MeasurementUnitListDialog(
                    selectedUnit = uiState.measurementUnit,
                    onConfirm = { unit -> onEvent(SettingsEvent.UpdateMeasurementUnit(unit)) },
                    onDismiss = { onEvent(SettingsEvent.DismissMeasurementUnitListDialog) },
                )
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
    SettingsContent(
        uiState = SettingsState.createToPreview(),
        onEvent = {},
        finishScreen = MutableSharedFlow(),
        toastEvent = MutableSharedFlow()
    )
}
