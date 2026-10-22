package com.felippeneves.kotlin_compose_weather_app.data.model.response

import com.google.gson.annotations.SerializedName

data class WeatherItemResponse(
    val dt: Int = 0,
    val main: MainResponse? = null,
    val weather: List<WeatherObjectResponse> = emptyList(),
    val wind: WindResponse? = null,
    val pop: Double = 0.0,
    @SerializedName("dt_txt")
    val dtTxt: String? = null,
    val sunrise: Int = 0,
    val sunset: Int = 0
)
