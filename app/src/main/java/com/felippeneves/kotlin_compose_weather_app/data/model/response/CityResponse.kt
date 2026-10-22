package com.felippeneves.kotlin_compose_weather_app.data.model.response

data class CityResponse(
    val coord: CoordResponse? = null,
    val country: String = "",
    val id: Int = 0,
    val name: String = "",
    val population: Int = 0,
    val timezone: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0
)
