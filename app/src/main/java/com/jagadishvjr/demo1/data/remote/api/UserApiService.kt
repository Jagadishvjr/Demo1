package com.jagadishvjr.demo1.data.remote.api

import com.jagadishvjr.demo1.common.AppResult
import com.jagadishvjr.demo1.data.remote.dto.FootballResult
import retrofit2.http.GET

interface UserApiService {
    @GET("fixtures")
    suspend fun getUsers(): FootballResult
}