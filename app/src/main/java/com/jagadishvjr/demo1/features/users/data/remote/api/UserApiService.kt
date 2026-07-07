package com.jagadishvjr.demo1.features.users.data.remote.api

import com.jagadishvjr.demo1.features.users.data.remote.dto.UserDto
import retrofit2.http.GET

interface UserApiService {

    @GET("users")
    suspend fun getUsers(): List<UserDto>
}
