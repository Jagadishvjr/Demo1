package com.jagadishvjr.demo1.features.users.domain.usecase

import com.jagadishvjr.demo1.features.users.domain.model.Coordinates
import javax.inject.Inject
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt

class CalculateDistanceUseCase @Inject constructor() : DistanceCalculatorUseCase {

    override operator fun invoke(from: Coordinates, to: Coordinates): Double {
        val earthRadiusKm = 6371.0
        val latDistance = Math.toRadians(to.latitude - from.latitude)
        val lngDistance = Math.toRadians(to.longitude - from.longitude)
        val startLat = Math.toRadians(from.latitude)
        val endLat = Math.toRadians(to.latitude)

        val haversine = sin(latDistance / 2).pow(2) +
            sin(lngDistance / 2).pow(2) * cos(startLat) * cos(endLat)
        val distance = 2 * earthRadiusKm * asin(sqrt(haversine))

        return round(distance * 100) / 100
    }
}
