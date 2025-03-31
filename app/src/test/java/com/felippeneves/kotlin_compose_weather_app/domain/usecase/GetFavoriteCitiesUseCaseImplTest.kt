package com.felippeneves.kotlin_compose_weather_app.domain.usecase

import com.felippeneves.kotlin_compose_weather_app.core.DataResult
import com.felippeneves.kotlin_compose_weather_app.domain.model.FavoriteCity
import com.felippeneves.kotlin_compose_weather_app.domain.model.FavoriteCityFactory
import com.felippeneves.kotlin_compose_weather_app.domain.repository.FavoriteCityRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetFavoriteCitiesUseCaseImplTest {

    // region helper fields

    private val favoriteCityRepositoryMock: FavoriteCityRepository = mockk()
    private val favoriteCitiesList = FavoriteCityFactory.createList()

    // endregion helper fields

    // region SUT

    private lateinit var sut: GetFavoriteCitiesUseCase

    // endregion SUT

    // region setup

    @BeforeEach
    fun setUp() {
        sut = GetFavoriteCitiesUseCaseImpl(repository = favoriteCityRepositoryMock)
    }

    // endregion setup

    // region test methods

    @Test
    fun `must call repository getAll exactly once`() = runTest {
        // Given
        coEvery { favoriteCityRepositoryMock.getAll() } returns flowOf(emptyList())
        val expectedInvocationCount = 1

        // When
        sut.invoke().first()

        // Then
        coVerify(exactly = expectedInvocationCount) { favoriteCityRepositoryMock.getAll() }
    }

    @Test
    fun `must emit Loading then Success from DataResult when repository returns FavoriteCity list`() =
        runTest {
            // Given
            coEvery { favoriteCityRepositoryMock.getAll() } returns flowOf(favoriteCitiesList)
            val expectedEmissionCount = 2
            val expectedLoading = DataResult.Loading
            val expectedSuccess = DataResult.Success(favoriteCitiesList)

            // When
            val result = sut.invoke().toList()

            // Then
            assertEquals(expectedEmissionCount, result.size)
            assertEquals(expectedLoading, result.first())
            assertEquals(expectedSuccess, result.last())
        }

    @Test
    fun `must emit Loading then Success with empty list when repository returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<FavoriteCity>()
        coEvery { favoriteCityRepositoryMock.getAll() } returns flowOf(emptyList)
        val expectedEmissionCount = 2
        val expectedLoading = DataResult.Loading
        val expectedSuccess = DataResult.Success(emptyList)

        // When
        val result = sut.invoke().toList()

        // Then
        assertEquals(expectedEmissionCount, result.size)
        assertEquals(expectedLoading, result.first())
        assertEquals(expectedSuccess, result.last())
    }

    @Test
    fun `must emit Loading then Failure from DataResult when repository throws an exception`() =
        runTest {
            // Given
            val exception = RuntimeException()
            coEvery { favoriteCityRepositoryMock.getAll() } throws exception
            val expectedEmissionCount = 2
            val expectedLoading = DataResult.Loading
            val expectedFailure = (DataResult.Failure(exception))

            // When
            val result = sut.invoke().toList()

            // Then
            assertEquals(expectedEmissionCount, result.size)
            assertEquals(expectedLoading, result.first())
            assertEquals(expectedFailure, result.last())
        }

    // endregion test methods
}