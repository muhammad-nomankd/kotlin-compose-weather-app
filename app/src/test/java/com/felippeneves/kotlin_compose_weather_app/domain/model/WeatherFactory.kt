package com.felippeneves.kotlin_compose_weather_app.domain.model

class WeatherFactory {
    companion object {
        fun create(): Weather =
            Weather(
                city = CityFactory.create(),
                weeklyWeatherList = WeatherItemFactory.createList()
            )
    }
}
