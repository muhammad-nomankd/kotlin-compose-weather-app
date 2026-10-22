package com.felippeneves.kotlin_compose_weather_app.data.model.response

data class WindResponse(
    val speed: Double = 0.0,
    val deg: Int = 0,
    val gust: Double? = null
)
