package com.jagadishvjr.demo1.data.remote.api

import com.jagadishvjr.demo1.data.remote.dto.UserDto
import com.jagadishvjr.demo1.data.remote.dto.UserDtoItem
import retrofit2.http.GET

interface UserAPiService {

    @GET("users")
    suspend fun getUsers(): List<UserDtoItem>
}