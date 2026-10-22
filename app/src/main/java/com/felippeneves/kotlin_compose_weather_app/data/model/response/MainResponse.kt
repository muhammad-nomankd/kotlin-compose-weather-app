package com.felippeneves.kotlin_compose_weather_app.data.model.response

import com.google.gson.annotations.SerializedName

data class MainResponse(
    val temp: Double = 0.0,
    @SerializedName("feels_like")
    val feelsLike: Double = 0.0,
    @SerializedName("temp_min")
    val tempMin: Double = 0.0,
    @SerializedName("temp_max")
    val tempMax: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0
)