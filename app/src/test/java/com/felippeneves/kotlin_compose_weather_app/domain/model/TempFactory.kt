package com.felippeneves.kotlin_compose_weather_app.domain.model

class TempFactory {
    companion object {
        fun create(): Temp =
            Temp(
                day = 32.03,
                min = 20.52,
                max = 32.7
            )

        fun create2(): Temp =
            Temp(
                day = 34.25,
                min = 20.32,
                max = 34.36
            )
    }
}