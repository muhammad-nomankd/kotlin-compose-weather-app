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
import androidx.compose.foundation.shape.CircleShape
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
        listOf(
            Color(0xFF162A5A),
            Color(0xFF1B3B6F)
        )
    } else {
        listOf(
            Color(0xFF2563EB),
            Color(0xFF3B82F6),
            Color(0xFF1D4ED8)
        )
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(26.dp),
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                )
                .padding(horizontal = 22.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1.3f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White.copy(alpha = 0.18f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "☀️",
                                fontSize = 12.sp
                            )
                            Text(
                                text = weather.weatherObject.condition.ifEmpty { weather.weatherObject.description }
                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = TemperatureFormatter.format(weather.temp.day, isImperial),
                        fontSize = 54.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 58.sp
                    )

                    Text(
                        text = "Feels like ${TemperatureFormatter.format(weather.temp.day + 2.0, isImperial)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f),
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Row(
                        modifier = Modifier.padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "↑", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                            Text(
                                text = TemperatureFormatter.format(weather.temp.max, isImperial),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "↓", color = Color(0xFF60A5FA), fontWeight = FontWeight.Bold)
                            Text(
                                text = TemperatureFormatter.format(weather.temp.min, isImperial),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFF59E0B).copy(alpha = 0.45f),
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(ApiUtil.buildIconUrl(weather.weatherObject.icon)),
                            contentDescription = weather.weatherObject.description,
                            modifier = Modifier.size(90.dp)
                        )
                    }
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
