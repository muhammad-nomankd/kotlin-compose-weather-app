package com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.felippeneves.kotlin_compose_weather_app.core.utils.ApiUtil
import com.felippeneves.kotlin_compose_weather_app.core.utils.DateUtil
import com.felippeneves.kotlin_compose_weather_app.core.utils.TemperatureFormatter
import com.felippeneves.kotlin_compose_weather_app.domain.model.WeatherItem

@Composable
fun DailyWeatherItem(
    modifier: Modifier = Modifier,
    weather: WeatherItem,
    isImperial: Boolean,
    isToday: Boolean = false
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = if (isToday) BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1.3f)
            ) {
                Text(
                    text = DateUtil.getDayOfWeek(weather.timestampDate),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = DateUtil.formatDate(weather.timestampDate),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                modifier = Modifier.weight(1.6f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = rememberAsyncImagePainter(ApiUtil.buildIconUrl(weather.weatherObject.icon)),
                    contentDescription = weather.weatherObject.description,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = weather.weatherObject.condition.ifEmpty { weather.weatherObject.description }
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = TemperatureFormatter.format(weather.temp.max, isImperial),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF97316)
                )
                Text(
                    text = "/",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = TemperatureFormatter.format(weather.temp.min, isImperial),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Details",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .size(18.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DailyWeatherItem(
        weather = WeatherItem.createToPreview(),
        isImperial = false,
        isToday = true
    )
}
