package com.felippeneves.kotlin_compose_weather_app.domain.model

class FavoriteCityFactory {
    companion object {

        const val CITY_1 = "Ribeirão Preto"
        const val COUNTRY_1 = "Brasil"

        const val CITY_2 = "London"
        const val COUNTRY_2 = "England"

        const val CITY_3 = "São Paulo"


        fun create(): FavoriteCity =
            FavoriteCity(
                city = CITY_1,
                country = COUNTRY_1
            )

        fun create2(): FavoriteCity =
            FavoriteCity(
                city = CITY_2,
                country = COUNTRY_2
            )

        fun createList(): List<FavoriteCity> =
            listOf(
                create(),
                create2(),
                create().copy(city = CITY_3)
            )
    }
}