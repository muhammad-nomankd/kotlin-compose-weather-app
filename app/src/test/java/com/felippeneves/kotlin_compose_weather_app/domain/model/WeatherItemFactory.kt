package com.felippeneves.kotlin_compose_weather_app.domain.model

class WeatherItemFactory {
    companion object {
        fun create(): WeatherItem =
            WeatherItem(
                timestampDate = 1743346800,
                humidity = 47,
                pressure = 1013,
                speed = 2.37,
                sunrise = 1743326278,
                sunset = 1743369182,
                weatherObject = WeatherObjectFactory.create(),
                temp = TempFactory.create()
            )

        fun create2(): WeatherItem =
            WeatherItem(
                timestampDate = 1743692400,
                humidity = 40,
                pressure = 1012,
                speed = 3.89,
                sunrise = 1743671948,
                sunset = 1743714565,
                weatherObject = WeatherObjectFactory.create2(),
                temp = TempFactory.create2()
            )

        fun createList(): List<WeatherItem> = listOf(
            create(),
            create2()
        )
    }
}