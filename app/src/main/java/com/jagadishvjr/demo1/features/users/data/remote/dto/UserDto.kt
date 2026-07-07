package com.jagadishvjr.demo1.features.users.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: AddressDto
)

data class AddressDto(
    @SerializedName("street")
    val street: String,
    @SerializedName("suite")
    val suite: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("zipcode")
    val zipcode: String,
    @SerializedName("geo")
    val geo: GeoDto
)

data class GeoDto(
    @SerializedName("lat")
    val latitude: String,
    @SerializedName("lng")
    val longitude: String
)
