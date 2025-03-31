package com.felippeneves.kotlin_compose_weather_app.domain.model

class CityFactory {
    companion object {
        const val CITY_1 = "Ribeirão Preto"
        const val COUNTRY_1 = "Brasil"

        fun create(): City =
            City(
                name = CITY_1,
                country = COUNTRY_1
            )
    }
}
