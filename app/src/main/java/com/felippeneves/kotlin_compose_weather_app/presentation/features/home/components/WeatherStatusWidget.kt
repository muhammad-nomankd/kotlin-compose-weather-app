package com.felippeneves.kotlin_compose_weather_app.presentation.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.felippeneves.kotlin_compose_weather_app.core.utils.ApiUtil
import com.felippeneves.kotlin_compose_weather_app.core.utils.TemperatureFormatter
import com.felippeneves.kotlin_compose_weather_app.domain.model.WeatherItem

@Composable
fun WeatherStatusWidget(
    modifier: Modifier = Modifier,
    weather: WeatherItem,
    isImperial: Boolean,
) {
    val isDark = isSystemInDarkTheme()
    val gradientColors = if (isDark) {
        listOf(Color(0xFF1E3A8A), Color(0xFF0F172A))
    } else {
        listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(ApiUtil.buildIconUrl(weather.weatherObject.icon)),
                    contentDescription = weather.weatherObject.description,
                    modifier = Modifier.size(100.dp)
                )

                Text(
                    text = TemperatureFormatter.format(weather.temp.day, isImperial),
                    fontSize = 52.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Surface(
                    modifier = Modifier.padding(top = 6.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = weather.weatherObject.condition.ifEmpty { weather.weatherObject.description },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "H: ${TemperatureFormatter.format(weather.temp.max, isImperial)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "L: ${TemperatureFormatter.format(weather.temp.min, isImperial)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.75f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    WeatherStatusWidget(
        weather = WeatherItem.createToPreview(),
        isImperial = false
    )
}
