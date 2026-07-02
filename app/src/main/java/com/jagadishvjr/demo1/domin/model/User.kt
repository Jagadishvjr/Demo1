package com.jagadishvjr.demo1.domin.model





data class User(
    val address: Address,
    val distanceFromCurrentUserKm: Double? = null,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)

data class Address(
    val city: String,
    val geo: Geo,
    val zipcode: String
)

data class Geo(
    val lat: Double,
    val lng: Double
)
