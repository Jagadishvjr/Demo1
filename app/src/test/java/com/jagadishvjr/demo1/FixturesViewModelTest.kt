package com.jagadishvjr.demo1

import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.fixtures.domain.model.Fixture
import com.jagadishvjr.demo1.features.fixtures.domain.usecase.GetFixturesUseCase
import com.jagadishvjr.demo1.features.fixtures.presentation.FixturesUiState
import com.jagadishvjr.demo1.features.fixtures.presentation.FixturesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FixturesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getFixturesUseCase: GetFixturesUseCase = mockk()

    @Test
    fun `fetchFixtures emits success when data is available`() = runTest {
        val fixtures = listOf(
            Fixture(id = 1L, name = "Team A", resultInfo = "Finished")
        )
        coEvery { getFixturesUseCase() } returns AppResult.Success(fixtures)
        val viewModel = FixturesViewModel(getFixturesUseCase)

        viewModel.fetchFixtures()
        advanceUntilIdle()

        assertEquals(FixturesUiState.Success(fixtures), viewModel.uiState.value)
    }

    @Test
    fun `fetchFixtures emits empty when list is empty`() = runTest {
        coEvery { getFixturesUseCase() } returns AppResult.Success(emptyList())
        val viewModel = FixturesViewModel(getFixturesUseCase)

        viewModel.fetchFixtures()
        advanceUntilIdle()

        assertEquals(FixturesUiState.Empty, viewModel.uiState.value)
    }

    @Test
    fun `fetchFixtures emits error when use case fails`() = runTest {
        coEvery { getFixturesUseCase() } returns AppResult.Error("Network error")
        val viewModel = FixturesViewModel(getFixturesUseCase)

        viewModel.fetchFixtures()
        advanceUntilIdle()

        assertEquals(FixturesUiState.Error("Network error"), viewModel.uiState.value)
    }
}
