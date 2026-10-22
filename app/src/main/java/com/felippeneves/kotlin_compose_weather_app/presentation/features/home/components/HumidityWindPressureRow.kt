package com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felippeneves.kotlin_compose_weather_app.R
import com.felippeneves.kotlin_compose_weather_app.core.utils.SpeedUtil
import com.felippeneves.kotlin_compose_weather_app.domain.model.WeatherItem

@Composable
fun HumidityWindPressureRow(
    modifier: Modifier = Modifier,
    weather: WeatherItem,
    isImperial: Boolean
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        WeatherStatCard(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.humidity,
            value = "${weather.humidity}%",
            label = "Humidity",
            iconBgColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            iconTint = MaterialTheme.colorScheme.primary
        )

        WeatherStatCard(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.pressure,
            value = "${weather.pressure} hPa",
            label = "Pressure",
            iconBgColor = Color(0xFF8B5CF6).copy(alpha = 0.15f),
            iconTint = Color(0xFF8B5CF6)
        )

        WeatherStatCard(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.wind,
            value = SpeedUtil.formatSpeed(weather.speed, isImperial),
            label = "Wind",
            iconBgColor = Color(0xFF10B981).copy(alpha = 0.15f),
            iconTint = Color(0xFF10B981)
        )
    }
}

@Composable
private fun WeatherStatCard(
    modifier: Modifier = Modifier,
    iconRes: Int,
    value: String,
    label: String,
    iconBgColor: Color,
    iconTint: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = iconBgColor,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    tint = iconTint,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(20.dp)
                )
            }

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    HumidityWindPressureRow(
        weather = WeatherItem.createToPreview(),
        isImperial = false
    )
}
