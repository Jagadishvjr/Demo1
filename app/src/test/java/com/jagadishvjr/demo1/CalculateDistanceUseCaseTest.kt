package com.jagadishvjr.demo1

import com.jagadishvjr.demo1.features.users.domain.model.Coordinates
import com.jagadishvjr.demo1.features.users.domain.usecase.CalculateDistanceUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CalculateDistanceUseCaseTest {

    private val calculateDistanceUseCase = CalculateDistanceUseCase()

    @Test
    fun `invoke returns zero when both coordinates are same`() {
        val point = Coordinates(latitude = 12.9716, longitude = 77.5946)

        val distance = calculateDistanceUseCase(point, point)

        assertEquals(0.0, distance, 0.0)
    }

    @Test
    fun `invoke returns expected rounded distance`() {
        val start = Coordinates(latitude = 12.9716, longitude = 77.5946)
        val end = Coordinates(latitude = 13.0827, longitude = 80.2707)

        val distance = calculateDistanceUseCase(start, end)

        assertTrue(distance > 280.0)
        assertTrue(distance < 300.0)
    }
}
