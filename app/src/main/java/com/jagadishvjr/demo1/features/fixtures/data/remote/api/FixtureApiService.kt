package com.jagadishvjr.demo1.features.fixtures.data.remote.api

import com.jagadishvjr.demo1.features.fixtures.data.remote.dto.FootballResponse
import retrofit2.http.GET

interface FixtureApiService {

    @GET("fixtures")
    suspend fun getFixtures(): FootballResponse
}
