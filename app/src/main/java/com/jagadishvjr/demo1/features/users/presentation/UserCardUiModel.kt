package com.jagadishvjr.demo1.features.users.presentation

data class UserCardUiModel(
    val id: Long,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val distanceKm: Double?
)
