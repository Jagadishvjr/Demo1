package com.jagadishvjr.demo1.data.remote.dto

data class AddressDto(
    val city: String,
    val geo: GeoDto,
    val zipcode: String
)

data class GeoDto(
    val lat: String,
    val lng: String
)
