package com.felippeneves.kotlin_compose_weather_app.domain.usecase

import com.felippeneves.kotlin_compose_weather_app.core.DataResult
import com.felippeneves.kotlin_compose_weather_app.core.exception.NetworkException
import com.felippeneves.kotlin_compose_weather_app.core.utils.Constants
import com.felippeneves.kotlin_compose_weather_app.domain.model.CityFactory
import com.felippeneves.kotlin_compose_weather_app.domain.model.WeatherFactory
import com.felippeneves.kotlin_compose_weather_app.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetWeatherByCityUseCaseImplTest {

    // region helper fields

    private val weatherRepositoryMock: WeatherRepository = mockk()
    private val query = CityFactory.CITY_1
    private val measurementUnit = Constants.METRIC_MEASUREMENT_UNIT
    private val weather = WeatherFactory.create()
    private val params = GetWeatherByCityUseCase.Params(query, measurementUnit)

    // endregion helper fields

    // region SUT

    private lateinit var sut: GetWeatherByCityUseCase

    // region SUT

    // region setup

    @BeforeEach
    fun setUp() {
        sut = GetWeatherByCityUseCaseImpl(repository = weatherRepositoryMock)
    }

    // endregion setup

    // region test methods

    @Test
    fun `must call repository getWeatherByCity exactly once`() = runTest {
        // Given
        coEvery { weatherRepositoryMock.getWeatherByCity(query, measurementUnit) } returns null
        val expectedInvocationCount = 1

        // When
        sut.invoke(params).first()

        // Then
        coVerify(exactly = expectedInvocationCount) {
            weatherRepositoryMock.getWeatherByCity(
                query,
                measurementUnit
            )
        }
    }

    @Test
    fun `must emit Loading then Success with weather when repository returns data`() = runTest {
        // Given
        coEvery { weatherRepositoryMock.getWeatherByCity(query, measurementUnit) } returns weather
        val expectedEmissionCount = 2
        val expectedLoading = DataResult.Loading
        val expectedSuccess = DataResult.Success(weather)

        // When
        val result = sut.invoke(params).toList()

        // Then
        assertEquals(expectedEmissionCount, result.size)
        assertEquals(expectedLoading, result.first())
        assertEquals(expectedSuccess, result.last())
    }

    @Test
    fun `must emit Loading then Success with null when repository returns null`() = runTest {
        // Given
        coEvery { weatherRepositoryMock.getWeatherByCity(query, measurementUnit) } returns null
        val expectedEmissionCount = 2
        val expectedLoading = DataResult.Loading
        val expectedSuccess = DataResult.Success(null)

        // When
        val result = sut.invoke(params).toList()

        // Then
        assertEquals(expectedEmissionCount, result.size)
        assertEquals(expectedLoading, result.first())
        assertEquals(expectedSuccess, result.last())
    }

    @Test
    fun `must emit Loading then Failure when repository throws NoInternetConnection`() = runTest {
        // Given
        val exception = NetworkException.NoInternetConnection
        coEvery { weatherRepositoryMock.getWeatherByCity(query, measurementUnit) } throws exception
        val expectedEmissionCount = 2
        val expectedLoading = DataResult.Loading
        val expectedFailure = DataResult.Failure(exception)

        // When
        val result = sut.invoke(params).toList()

        // Then
        assertEquals(expectedEmissionCount, result.size)
        assertEquals(expectedLoading, result.first())
        assertEquals(expectedFailure, result.last())
    }

    @Test
    fun `must emit Loading then Failure when repository throws Timeout`() = runTest {
        // Given
        val exception = NetworkException.Timeout
        coEvery { weatherRepositoryMock.getWeatherByCity(query, measurementUnit) } throws exception
        val expectedEmissionCount = 2
        val expectedLoading = DataResult.Loading
        val expectedFailure = DataResult.Failure(exception)

        // When
        val result = sut.invoke(params).toList()

        // Then
        assertEquals(expectedEmissionCount, result.size)
        assertEquals(expectedLoading, result.first())
        assertEquals(expectedFailure, result.last())
    }

    @Test
    fun `must emit Loading then Failure when repository throws ServerError`() = runTest {
        // Given
        val exception = NetworkException.ServerError(500, "Internal Server Error")
        coEvery { weatherRepositoryMock.getWeatherByCity(query, measurementUnit) } throws exception
        val expectedEmissionCount = 2
        val expectedLoading = DataResult.Loading
        val expectedFailure = DataResult.Failure(exception)

        // When
        val result = sut.invoke(params).toList()

        // Then
        assertEquals(expectedEmissionCount, result.size)
        assertEquals(expectedLoading, result.first())
        assertEquals(expectedFailure, result.last())
    }

    @Test
    fun `must emit Loading then Failure when repository throws general Exception`() = runTest {
        // Given
        val exception = RuntimeException()
        coEvery { weatherRepositoryMock.getWeatherByCity(query, measurementUnit) } throws exception
        val expectedEmissionCount = 2
        val expectedLoading = DataResult.Loading
        val expectedFailure = DataResult.Failure(exception)

        // When
        val result = sut.invoke(params).toList()

        // Then
        assertEquals(expectedEmissionCount, result.size)
        assertEquals(expectedLoading, result.first())
        assertEquals(expectedFailure, result.last())
    }

    // endregion test methods
}
