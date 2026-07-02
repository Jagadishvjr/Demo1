package com.jagadishvjr.demo1.domain.usecase

import com.jagadishvjr.demo1.common.AppResult
import com.jagadishvjr.demo1.domain.model.Fixure
import com.jagadishvjr.demo1.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetFixureUseCaseTest {

    private val repository: UserRepository = mockk()
    private val useCase = GetFixureUseCase(repository)

    @Test
    fun `invoke returns success when repository returns success`() = runTest {
        val fixtures = listOf(
            Fixure(id = 2L, name = "Team B vs Team C", resultInfo = "Finished"),
            Fixure(id = 1L, name = "Team A vs Team B", resultInfo = "Finished")
        )
        coEvery { repository.getFixtures() } returns AppResult.Success(fixtures)

        val result = useCase()

        assertTrue(result is AppResult.Success)
        assertEquals(
            listOf(
                Fixure(id = 1L, name = "Team A vs Team B", resultInfo = "Finished"),
                Fixure(id = 2L, name = "Team B vs Team C", resultInfo = "Finished")
            ),
            (result as AppResult.Success).data
        )
    }


    @Test
    fun `invoke removes fixtures with blank names`() = runTest {
        val fixtures = listOf(
            Fixure(id = 1L, name = "", resultInfo = "Finished"),
            Fixure(id = 2L, name = "Team B vs Team C", resultInfo = "Finished")
        )
        coEvery { repository.getFixtures() } returns AppResult.Success(fixtures)

        val result = useCase()

        assertTrue(result is AppResult.Success)
        assertEquals(
            listOf(Fixure(id = 2L, name = "Team B vs Team C", resultInfo = "Finished")),
            (result as AppResult.Success).data
        )
    }

    @Test
    fun `invoke returns error when repository returns error`() = runTest {
        coEvery { repository.getFixtures() } returns AppResult.Error("Network error")

        val result = useCase()

        assertTrue(result is AppResult.Error)
        assertEquals("Network error", (result as AppResult.Error).message)
    }
}


