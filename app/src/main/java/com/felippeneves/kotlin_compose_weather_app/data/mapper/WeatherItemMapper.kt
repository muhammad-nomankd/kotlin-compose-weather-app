package com.felippeneves.kotlin_compose_weather_app.data.mapper

import com.felippeneves.kotlin_compose_weather_app.data.model.response.WeatherItemResponse
import com.felippeneves.kotlin_compose_weather_app.domain.model.Temp
import com.felippeneves.kotlin_compose_weather_app.domain.model.WeatherItem
import com.felippeneves.kotlin_compose_weather_app.domain.model.WeatherObject

fun List<WeatherItemResponse>.toWeatherItemList() = map { it.toWeatherItem() }

fun WeatherItemResponse.toWeatherItem(
    sunrise: Int = this.sunrise,
    sunset: Int = this.sunset,
    minTemp: Double = main?.tempMin ?: main?.temp ?: 0.0,
    maxTemp: Double = main?.tempMax ?: main?.temp ?: 0.0
): WeatherItem {
    val tempVal = main?.temp ?: 0.0
    val weatherObj = if (weather.isNotEmpty()) {
        weather[0].toWeatherObject()
    } else {
        WeatherObject(description = "", icon = "", id = 0, condition = "")
    }

    return WeatherItem(
        timestampDate = dt,
        humidity = main?.humidity ?: 0,
        pressure = main?.pressure ?: 0,
        speed = wind?.speed ?: 0.0,
        sunrise = sunrise,
        sunset = sunset,
        weatherObject = weatherObj,
        temp = Temp(
            day = tempVal,
            min = minTemp,
            max = maxTemp
        )
    )
}
