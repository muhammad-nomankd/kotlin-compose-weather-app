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
import androidx.compose.ui.unit.sp
import com.felippeneves.kotlin_compose_weather_app.R
import com.felippeneves.kotlin_compose_weather_app.core.utils.DateUtil
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
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Card 1: Humidity
        WeatherMetricCard(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.humidity,
            label = "Humidity",
            value = "${weather.humidity}%",
            badgeBgColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            badgeIconTint = MaterialTheme.colorScheme.primary
        )

        // Card 2: Wind Speed
        WeatherMetricCard(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.wind,
            label = "Wind Speed",
            value = SpeedUtil.formatSpeed(weather.speed, isImperial),
            badgeBgColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
            badgeIconTint = MaterialTheme.colorScheme.secondary
        )

        // Card 3: Pressure
        WeatherMetricCard(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.pressure,
            label = "Pressure",
            value = "${weather.pressure} hPa",
            badgeBgColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
            badgeIconTint = MaterialTheme.colorScheme.tertiary
        )

        // Card 4: Sunrise / Sunset
        SunTimesMetricCard(
            modifier = Modifier.weight(1f),
            sunriseStr = DateUtil.formatDateTime(weather.sunrise),
            sunsetStr = DateUtil.formatDateTime(weather.sunset)
        )
    }
}

@Composable
private fun WeatherMetricCard(
    modifier: Modifier = Modifier,
    iconRes: Int,
    label: String,
    value: String,
    badgeBgColor: Color,
    badgeIconTint: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = CircleShape,
                color = badgeBgColor,
                modifier = Modifier.size(34.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    tint = badgeIconTint,
                    modifier = Modifier
                        .padding(7.dp)
                        .size(18.dp)
                )
            }

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 10.dp)
            )

            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun SunTimesMetricCard(
    modifier: Modifier = Modifier,
    sunriseStr: String,
    sunsetStr: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFFFB300).copy(alpha = 0.25f),
                modifier = Modifier.size(34.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sunrise),
                    contentDescription = "Sun",
                    tint = Color(0xFFFFB300),
                    modifier = Modifier
                        .padding(6.dp)
                        .size(20.dp)
                )
            }

            Column(
                modifier = Modifier.padding(top = 6.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(text = "↑", color = MaterialTheme.colorScheme.primary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(text = sunriseStr, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(text = "↓", color = MaterialTheme.colorScheme.error, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(text = sunsetStr, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                }
            }
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
