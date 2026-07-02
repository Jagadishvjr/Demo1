package com.jagadishvjr.demo1.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FootballResult(
    @SerializedName("data")
    val data: List<FixureDto>
)

data class FixureDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("result_info")
    val resultInfo: String
)