package com.jagadishvjr.demo1

import com.jagadishvjr.demo1.domin.model.Address
import com.jagadishvjr.demo1.domin.model.Geo
import com.jagadishvjr.demo1.domin.model.User
import com.jagadishvjr.demo1.domin.repository.UserRepository
import com.jagadishvjr.demo1.domin.usecase.GetUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GetUserUseCaseTest {

    @Test
    fun `invoke returns only users whose names start with vowels`() = runTest {
        val users = listOf(
            createUser(id = 1, name = "Alice"),
            createUser(id = 2, name = "bob"),
            createUser(id = 3, name = "Esha"),
            createUser(id = 4, name = "charlie"),
            createUser(id = 5, name = "uma")
        )
        val repository = mockk<UserRepository>()
        coEvery { repository.getUsers() } returns users

        val useCase = GetUserUseCase(repository)

        val result = useCase()

        assertEquals(listOf("Alice", "Esha", "uma"), result.map { it.name })
        assertNotNull(result[0].distanceFromCurrentUserKm)
        assertTrue(result[0].distanceFromCurrentUserKm!! > 0)
        coVerify(exactly = 1) { repository.getUsers() }
    }

    private fun createUser(id: Int, name: String): User {
        return User(
            id = id,
            name = name,
            username = name.lowercase(),
            email = "abc@mail.com",
            website = "example.com",
            phone = "345346777",
            address = Address(
                city = "Hyd",
                geo = Geo(
                    lat = 17.3850 + id,
                    lng = 78.4867 + id
                ),
                zipcode = "500086"
            )
        )
    }
}
