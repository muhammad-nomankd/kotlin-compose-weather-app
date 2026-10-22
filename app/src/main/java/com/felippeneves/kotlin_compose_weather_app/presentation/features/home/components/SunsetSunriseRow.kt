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
import com.felippeneves.kotlin_compose_weather_app.core.utils.DateUtil
import com.felippeneves.kotlin_compose_weather_app.domain.model.WeatherItem

@Composable
fun SunsetSunriseRow(
    modifier: Modifier = Modifier,
    weather: WeatherItem
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SunTimeCard(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.sunrise,
            timeStr = DateUtil.formatDateTime(weather.sunrise),
            label = "Sunrise",
            accentColor = Color(0xFFF59E0B)
        )

        SunTimeCard(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.sunset,
            timeStr = DateUtil.formatDateTime(weather.sunset),
            label = "Sunset",
            accentColor = Color(0xFFEF4444)
        )
    }
}

@Composable
private fun SunTimeCard(
    modifier: Modifier = Modifier,
    iconRes: Int,
    timeStr: String,
    label: String,
    accentColor: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                shape = CircleShape,
                color = accentColor.copy(alpha = 0.15f),
                modifier = Modifier.size(42.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    tint = accentColor,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(26.dp)
                )
            }

            Column(
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Text(
                    text = timeStr,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SunsetSunriseRow(weather = WeatherItem.createToPreview())
}
