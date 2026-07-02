package com.jagadishvjr.demo1.presentation

import com.jagadishvjr.demo1.MainDispatcherRule
import com.jagadishvjr.demo1.common.AppResult
import com.jagadishvjr.demo1.domain.model.Fixure
import com.jagadishvjr.demo1.domain.usecase.GetFixureUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FixtureViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getFixureUseCase: GetFixureUseCase = mockk()

    @Test
    fun `fetchFixtures sets success state when data is available`() = runTest {
        val fixtures = listOf(
            Fixure(id = 1L, name = "Team A vs Team B", resultInfo = "Finished")
        )
        coEvery { getFixureUseCase() } returns AppResult.Success(fixtures)
        val viewModel = FixtureViewModel(getFixureUseCase)

        viewModel.fetchFixtures()
        advanceUntilIdle()

        assertEquals(FixtureListUiState.Success(fixtures), viewModel.uiState.value)
    }

    @Test
    fun `fetchFixtures sets empty state when data is empty`() = runTest {
        coEvery { getFixureUseCase() } returns AppResult.Success(emptyList())
        val viewModel = FixtureViewModel(getFixureUseCase)

        viewModel.fetchFixtures()
        advanceUntilIdle()

        assertEquals(FixtureListUiState.Empty, viewModel.uiState.value)
    }

    @Test
    fun `fetchFixtures sets error state when use case returns error`() = runTest {
        coEvery { getFixureUseCase() } returns AppResult.Error("Something went wrong")
        val viewModel = FixtureViewModel(getFixureUseCase)

        viewModel.fetchFixtures()
        advanceUntilIdle()

        assertEquals(
            FixtureListUiState.Error("Something went wrong"),
            viewModel.uiState.value
        )
    }
}
