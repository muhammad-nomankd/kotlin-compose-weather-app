package com.felippeneves.kotlin_compose_weather_app.domain.model

class FavoriteCityFactory {
    companion object {

        const val CITY_1 = "Ribeirão Preto"
        const val COUNTRY_1 = "Brasil"

        fun create(): FavoriteCity =
            FavoriteCity(
                city = CITY_1,
                country = COUNTRY_1
            )
    }
}