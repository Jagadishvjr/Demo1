package com.jagadishvjr.demo1

import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.users.domain.model.Address
import com.jagadishvjr.demo1.features.users.domain.model.Geo
import com.jagadishvjr.demo1.features.users.domain.model.User
import com.jagadishvjr.demo1.features.users.domain.repository.UserRepository
import com.jagadishvjr.demo1.features.users.domain.usecase.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetUsersUseCaseTest {

    private val repository: UserRepository = mockk()
    private val getUsersUseCase = GetUsersUseCase(repository)

    @Test
    fun `invoke filters blank names and sorts users`() = runTest {
        val users = listOf(
            user(id = 1L, name = "charlie"),
            user(id = 2L, name = " "),
            user(id = 3L, name = "Alice")
        )
        coEvery { repository.getUsers() } returns AppResult.Success(users)

        val result = getUsersUseCase()

        assertTrue(result is AppResult.Success)
        assertEquals(
            listOf(user(id = 3L, name = "Alice"), user(id = 1L, name = "charlie")),
            (result as AppResult.Success).data
        )
    }

    @Test
    fun `invoke returns error when repository fails`() = runTest {
        val error = AppResult.Error("Network error")
        coEvery { repository.getUsers() } returns error

        val result = getUsersUseCase()

        assertEquals(error, result)
    }

    private fun user(id: Long, name: String): User {
        return User(
            id = id,
            name = name,
            address = Address(
                street = "Street",
                suite = "Suite",
                city = "City",
                zipcode = "12345"
            ),
            geo = Geo(
                latitude = 12.34,
                longitude = 56.78
            )
        )
    }
}
