package com.felippeneves.kotlin_compose_weather_app.domain.usecase

import com.felippeneves.kotlin_compose_weather_app.core.DataResult
import com.felippeneves.kotlin_compose_weather_app.domain.model.FavoriteCityFactory
import com.felippeneves.kotlin_compose_weather_app.domain.repository.FavoriteCityRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddFavoriteCityUseCaseImplTest {

    // region helper fields

    private val favoriteCityRepositoryMock: FavoriteCityRepository = mockk()

    private val favoriteCity = FavoriteCityFactory.create()

    // endregion helper fields

    // region SUT

    private lateinit var sut: AddFavoriteCityUseCase

    // endregion SUT

    // region setup

    @BeforeEach
    fun setUp() {
        sut = AddFavoriteCityUseCaseImpl(repository = favoriteCityRepositoryMock)
    }

    // endregion setup

    // region test methods

    @Test
    fun `must call repository save exactly once`() = runTest {
        // Given
        coEvery { favoriteCityRepositoryMock.save(favoriteCity) } returns Unit
        val params = AddFavoriteCityUseCase.Params(favoriteCity)
        val expectedInvocationCount = 1

        // When
        sut.invoke(params).first()

        // Then
        coVerify(exactly = expectedInvocationCount) { favoriteCityRepositoryMock.save(favoriteCity) }
    }

    @Test
    fun `must emit Loading then Success from DataResult when repository returns Unit`() = runTest {
        //Given
        coEvery { favoriteCityRepositoryMock.save(favoriteCity) } returns Unit
        val params = AddFavoriteCityUseCase.Params(favoriteCity)
        val expectedEmissionCount = 2
        val expectedLoading = DataResult.Loading
        val expectedSuccess = DataResult.Success(Unit)

        //When
        val result = sut.invoke(params).toList()

        //Then
        assertEquals(expectedEmissionCount, result.size)
        assertEquals(expectedLoading, result.first())
        assertEquals(expectedSuccess, result.last())
    }

    @Test
    fun `must emit Loading then Failure when repository throws an exception`() = runTest {
        // Given
        val exception = RuntimeException()
        coEvery { favoriteCityRepositoryMock.save(favoriteCity) } throws exception
        val params = AddFavoriteCityUseCase.Params(favoriteCity)
        val expectedEmissionCount = 2
        val expectedLoading = DataResult.Loading
        val expectedFailure = (DataResult.Failure(exception))

        // When
        val result = sut.invoke(params).toList()

        // Then
        assertEquals(expectedEmissionCount, result.size)
        assertEquals(expectedLoading, result.first())
        assertEquals(expectedFailure, result.last())
    }

    // endregion test methods
}