package com.jagadishvjr.demo1.features.users.domain.model

data class User(
    val id: Long,
    val name: String,
    val address: Address,
    val geo: Geo
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String
) {
    fun formatted(): String {
        return listOf(street, suite, city, zipcode)
            .filter { it.isNotBlank() }
            .joinToString(", ")
    }
}

data class Geo(
    val latitude: Double,
    val longitude: Double
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

data class UserDistance(
    val user: User,
    val distanceKm: Double?
)
