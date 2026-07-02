package com.jagadishvjr.demo1.domain.model

import com.google.gson.annotations.SerializedName

data class Fixure(
    val id: Long,
    val name: String,
    val resultInfo: String
)