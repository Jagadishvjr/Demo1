package com.jagadishvjr.demo1

import com.jagadishvjr.demo1.domin.model.Address
import com.jagadishvjr.demo1.domin.model.Geo
import com.jagadishvjr.demo1.domin.model.User
import com.jagadishvjr.demo1.domin.usecase.GetUserUseCase
import com.jagadishvjr.presentation.UserUiState
import com.jagadishvjr.presentation.UserViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class UserViewModelTest{

    @get: Rule
    val testDispatcher = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetchUsers returns success when use case returns data`() = runTest {

        val fakeUsers = listOf(
            User(
                id = 101,
                name = "Jagdish",
                distanceFromCurrentUserKm = 12.5,
                username = "vjr",
                email = "vjr@mail.com",
                website = "abc",
                phone = "9603343",
                address = Address(
                    city = "Hyd",
                    geo = Geo(
                        lat = 17.3850,
                        lng = 78.4867
                    ),
                    zipcode = "500086"
                )
            )
        )

        val useCase = mockk<GetUserUseCase>()

        coEvery { useCase.invoke() } returns fakeUsers

        val viewModel = UserViewModel(useCase)
        viewModel.fetchUsers()

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UserUiState.Success)
        assertEquals(fakeUsers, (state as UserUiState.Success).list)

        //check name
        assertEquals(fakeUsers[0].name, (state as UserUiState.Success).list[0].name)

        //check city
        assertEquals(fakeUsers[0].address.city, (state as UserUiState.Success).list[0].address.city)


        coVerify(exactly = 1) { useCase.invoke() }

    }


}
