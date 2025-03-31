package com.felippeneves.kotlin_compose_weather_app.domain.model

class WeatherObjectFactory {
    companion object {
        fun create(): WeatherObject =
            WeatherObject(
                id = 500,
                description = "light rain",
                icon = "10d",
                condition = "Rain"
            )

        fun create2(): WeatherObject =
            WeatherObject(
                id = 501,
                description = "moderate rain",
                icon = "10d",
                condition = "Rain"
            )
    }
}