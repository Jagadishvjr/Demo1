package com.jagadishvjr.demo1

import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.fixtures.domain.model.Fixture
import com.jagadishvjr.demo1.features.fixtures.domain.repository.FixtureRepository
import com.jagadishvjr.demo1.features.fixtures.domain.usecase.GetFixturesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetFixturesUseCaseTest {

    private val repository: FixtureRepository = mockk()
    private val getFixturesUseCase = GetFixturesUseCase(repository)

    @Test
    fun `invoke filters blank fixture names`() = runTest {
        val fixtures = listOf(
            Fixture(id = 1L, name = "Team A", resultInfo = "Finished"),
            Fixture(id = 2L, name = " ", resultInfo = "Postponed")
        )
        coEvery { repository.getFixtures() } returns AppResult.Success(fixtures)

        val result = getFixturesUseCase()

        assertTrue(result is AppResult.Success)
        assertEquals(listOf(fixtures.first()), (result as AppResult.Success).data)
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        val error = AppResult.Error("Network error")
        coEvery { repository.getFixtures() } returns error

        val result = getFixturesUseCase()

        assertEquals(error, result)
    }
}
