package com.jagadishvjr.demo1.features.fixtures.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FootballResponse(
    @SerializedName("data")
    val data: List<FixtureDto>
)

data class FixtureDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("result_info")
    val resultInfo: String
)
