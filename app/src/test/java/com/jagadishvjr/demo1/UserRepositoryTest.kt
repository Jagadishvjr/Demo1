package com.jagadishvjr.demo1

import com.jagadishvjr.demo1.data.remote.UserApiService
import com.jagadishvjr.demo1.data.remote.dto.AddressDto
import com.jagadishvjr.demo1.data.remote.dto.UserDto
import com.jagadishvjr.demo1.data.repository.UserRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryTest {

    @Test
    fun `getUsers returns mapped users from api`() = runTest {
        val userDtos = listOf(
            UserDto(
                id = 101,
                name = "Jagdish",
                username = "vjr",
                email = "vjr@mail.com",
                website = "abc",
                phone = "9603343",
                address = AddressDto(
                    city = "Hyd",
                    zipcode = "500086"
                )
            )
        )
        val userApiService = mockk<UserApiService>()
        coEvery { userApiService.getUsers() } returns userDtos

        val repository = UserRepositoryImpl(userApiService)

        val result = repository.getUsers()

        assertEquals(1, result.size)
        assertEquals("Jagdish", result[0].name)
        assertEquals("Hyd", result[0].address.city)
        assertEquals("500086", result[0].address.zipcode)
        coVerify(exactly = 1) { userApiService.getUsers() }
    }
}
