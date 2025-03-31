package com.felippeneves.kotlin_compose_weather_app.domain.usecase

import com.felippeneves.kotlin_compose_weather_app.core.DataResult
import com.felippeneves.kotlin_compose_weather_app.core.utils.Constants
import com.felippeneves.kotlin_compose_weather_app.domain.repository.MeasurementUnitRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetMeasurementUnitUseCaseImplTest {

    // region helper fields

    private val measurementUnitRepositoryMock: MeasurementUnitRepository = mockk()
    private val measurementUnit = Constants.IMPERIAL_MEASUREMENT_UNIT

    // endregion helper fields

    // region SUT

    private lateinit var sut: GetMeasurementUnitUseCase

    // region SUT

    // region setup

    @BeforeEach
    fun setUp() {
        sut = GetMeasurementUnitUseCaseImpl(repository = measurementUnitRepositoryMock)
    }

    // endregion setup

    // region test methods

    @Test
    fun `must call repository getMeasurementUnit exactly once`() = runTest {
        // Given
        coEvery { measurementUnitRepositoryMock.getMeasurementUnit() } returns flowOf(null)
        val expectedInvocationCount = 1

        // When
        sut.invoke().first()

        // Then
        coVerify(exactly = expectedInvocationCount) { measurementUnitRepositoryMock.getMeasurementUnit() }
    }

    @Test
    fun `must emit Loading then Success with actual measurement unit when repository returns non-null`() =
        runTest {
            // Given
            coEvery { measurementUnitRepositoryMock.getMeasurementUnit() } returns flowOf(
                measurementUnit
            )
            val expectedEmissionCount = 2
            val expectedLoading = DataResult.Loading
            val expectedSuccess = DataResult.Success(measurementUnit)

            // When
            val result = sut.invoke().toList()

            // Then
            assertEquals(expectedEmissionCount, result.size)
            assertEquals(expectedLoading, result.first())
            assertEquals(expectedSuccess, result.last())
        }

    @Test
    fun `must emit Loading then Success with default unit when repository returns null`() =
        runTest {
            // Given
            coEvery { measurementUnitRepositoryMock.getMeasurementUnit() } returns flowOf(null)
            val expectedEmissionCount = 2
            val expectedLoading = DataResult.Loading
            val expectedSuccess = DataResult.Success(Constants.DEFAULT_MEASUREMENT_UNIT)

            // When
            val result = sut.invoke().toList()

            // Then
            assertEquals(expectedEmissionCount, result.size)
            assertEquals(expectedLoading, result.first())
            assertEquals(expectedSuccess, result.last())
        }

    @Test
    fun `must emit Loading then Failure when repository throws exception`() = runTest {
        // Given
        val exception = RuntimeException()
        coEvery { measurementUnitRepositoryMock.getMeasurementUnit() } throws exception
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
