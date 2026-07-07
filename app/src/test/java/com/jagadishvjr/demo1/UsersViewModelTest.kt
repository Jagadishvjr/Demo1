package com.jagadishvjr.demo1

import com.jagadishvjr.demo1.core.location.LocationTracker
import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.users.domain.model.Address
import com.jagadishvjr.demo1.features.users.domain.model.Coordinates
import com.jagadishvjr.demo1.features.users.domain.model.Geo
import com.jagadishvjr.demo1.features.users.domain.model.User
import com.jagadishvjr.demo1.features.users.domain.usecase.DistanceCalculatorUseCase
import com.jagadishvjr.demo1.features.users.domain.usecase.GetUsersUseCase
import com.jagadishvjr.demo1.features.users.presentation.UsersUiState
import com.jagadishvjr.demo1.features.users.presentation.UsersViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUsersUseCase: GetUsersUseCase = mockk()
    private val locationTracker: LocationTracker = mockk()
    private val calculateDistanceUseCase: DistanceCalculatorUseCase = mockk()
    private val calculateDistanceWithLocationApiUseCase: DistanceCalculatorUseCase = mockk()

    @Test
    fun `fetchUsers emits success with distance when location is available`() = runTest {
        val users = listOf(
            User(
                id = 1L,
                name = "Leanne Graham",
                address = Address(
                    street = "Kulas Light",
                    suite = "Apt. 556",
                    city = "Gwenborough",
                    zipcode = "92998-3874"
                ),
                geo = Geo(
                    latitude = -37.3159,
                    longitude = 81.1496
                )
            )
        )
        coEvery { getUsersUseCase() } returns AppResult.Success(users)
        coEvery { locationTracker.getCurrentLocation() } returns AppResult.Success(
            Coordinates(latitude = -37.0, longitude = 81.0)
        )
        coEvery {
            calculateDistanceWithLocationApiUseCase(any(), any())
        } returns 42.5
        val viewModel = UsersViewModel(
            getUsersUseCase,
            locationTracker,
            calculateDistanceUseCase,
            calculateDistanceWithLocationApiUseCase
        )

        viewModel.fetchUsers(shouldLoadDistance = true)
        advanceUntilIdle()

        val state = viewModel.uiState.value as UsersUiState.Success
        assertEquals(1, state.users.size)
        assertEquals(42.5, state.users.first().distanceKm)
    }

    @Test
    fun `fetchUsers emits empty when list is empty`() = runTest {
        coEvery { getUsersUseCase() } returns AppResult.Success(emptyList())
        val viewModel = UsersViewModel(
            getUsersUseCase,
            locationTracker,
            calculateDistanceUseCase,
            calculateDistanceWithLocationApiUseCase
        )

        viewModel.fetchUsers(shouldLoadDistance = false)
        advanceUntilIdle()

        assertEquals(UsersUiState.Empty, viewModel.uiState.value)
    }

    @Test
    fun `fetchUsers emits error when use case fails`() = runTest {
        coEvery { getUsersUseCase() } returns AppResult.Error("Network error")
        val viewModel = UsersViewModel(
            getUsersUseCase,
            locationTracker,
            calculateDistanceUseCase,
            calculateDistanceWithLocationApiUseCase
        )

        viewModel.fetchUsers(shouldLoadDistance = false)
        advanceUntilIdle()

        assertEquals(UsersUiState.Error("Network error"), viewModel.uiState.value)
    }

    @Test
    fun `fetchUsers emits success without distance when permission is denied`() = runTest {
        val users = listOf(
            User(
                id = 1L,
                name = "Leanne Graham",
                address = Address(
                    street = "Kulas Light",
                    suite = "Apt. 556",
                    city = "Gwenborough",
                    zipcode = "92998-3874"
                ),
                geo = Geo(
                    latitude = -37.3159,
                    longitude = 81.1496
                )
            )
        )
        coEvery { getUsersUseCase() } returns AppResult.Success(users)
        val viewModel = UsersViewModel(
            getUsersUseCase,
            locationTracker,
            calculateDistanceUseCase,
            calculateDistanceWithLocationApiUseCase
        )

        viewModel.fetchUsers(shouldLoadDistance = false)
        advanceUntilIdle()

        val state = viewModel.uiState.value as UsersUiState.Success
        assertEquals("Location permission denied. Showing users without distance.", state.infoMessage)
        assertEquals(null, state.users.first().distanceKm)
    }
}
