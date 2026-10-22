package com.felippeneves.kotlin_compose_weather_app.data.mapper

import com.felippeneves.kotlin_compose_weather_app.core.utils.DateUtil
import com.felippeneves.kotlin_compose_weather_app.data.model.response.WeatherResponse
import com.felippeneves.kotlin_compose_weather_app.domain.model.Weather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun WeatherResponse.toWeather(): Weather {
    val sunriseTime = city.sunrise
    val sunsetTime = city.sunset

    val dailyItems = if (list.isEmpty()) {
        emptyList()
    } else {
        list.groupBy { DateUtil.formatDate(it.dt) }
            .map { (_, itemsOfDay) ->
                val representative = itemsOfDay.minByOrNull { item ->
                    val hourStr = SimpleDateFormat("HH", Locale.getDefault()).format(Date(item.dt.toLong() * 1000))
                    val hour = hourStr.toIntOrNull() ?: 12
                    Math.abs(hour - 12)
                } ?: itemsOfDay.first()

                val minTemp = itemsOfDay.minOfOrNull { it.main?.tempMin ?: it.main?.temp ?: 0.0 } ?: 0.0
                val maxTemp = itemsOfDay.maxOfOrNull { it.main?.tempMax ?: it.main?.temp ?: 0.0 } ?: 0.0

                representative.toWeatherItem(
                    sunrise = if (representative.sunrise != 0) representative.sunrise else sunriseTime,
                    sunset = if (representative.sunset != 0) representative.sunset else sunsetTime,
                    minTemp = minTemp,
                    maxTemp = maxTemp
                )
            }
    }

    return Weather(
        city = city.toCity(),
        weeklyWeatherList = dailyItems
    )
}
