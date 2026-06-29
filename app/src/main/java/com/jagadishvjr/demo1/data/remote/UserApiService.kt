package com.jagadishvjr.demo1.data.remote

import com.jagadishvjr.demo1.data.remote.dto.UserDto
import retrofit2.http.GET

interface UserApiService {

    @GET("users")
    suspend fun getUsers() : List<UserDto>
}