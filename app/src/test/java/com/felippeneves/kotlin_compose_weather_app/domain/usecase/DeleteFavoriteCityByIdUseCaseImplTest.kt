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

class DeleteFavoriteCityByIdUseCaseImplTest {
    
    // region helper fields

    private val favoriteCityRepositoryMock: FavoriteCityRepository = mockk()
    private val city = FavoriteCityFactory.CITY_1

    // endregion helper fields

    // region SUT

    private lateinit var sut: DeleteFavoriteCityByIdUseCase

    // endregion SUT

    // region setup

    @BeforeEach
    fun setUp() {
        sut = DeleteFavoriteCityByIdUseCaseImpl(favoriteCityRepositoryMock)
    }

    // endregion setup

    // region test methods

    @Test
    fun `must call repository deleteById exactly once`() = runTest {
        // Given
        coEvery { favoriteCityRepositoryMock.deleteById(city) } returns Unit
        val params = DeleteFavoriteCityByIdUseCase.Params(city)
        val expectedInvocationCount = 1

        // When
        sut.invoke(params).first()

        // Then
        coVerify(exactly = expectedInvocationCount) { favoriteCityRepositoryMock.deleteById(city) }
    }

    @Test
    fun `must emit Loading then Success from DataResult when repository returns Unit`() = runTest {
        //Given
        coEvery { favoriteCityRepositoryMock.deleteById(city) } returns Unit
        val params = DeleteFavoriteCityByIdUseCase.Params(city)
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
    fun `must emit Loading then Failure from DataResult when repository throws exception`() =
        runTest {
            //Given
            val exception = RuntimeException()
            coEvery { favoriteCityRepositoryMock.deleteById(city) } throws exception
            val params = DeleteFavoriteCityByIdUseCase.Params(city)
            val expectedEmissionCount = 2
            val expectedLoading = DataResult.Loading
            val expectedFailure = (DataResult.Failure(exception))

            //When
            val result = sut.invoke(params).toList()

            //Then
            assertEquals(expectedEmissionCount, result.size)
            assertEquals(expectedLoading, result.first())
            assertEquals(expectedFailure, result.last())
        }

    // endregion test methods
}