package com.jagadishvjr.demo1.data.remote.dto

data class UserDto(
    val address: AddressDto,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)