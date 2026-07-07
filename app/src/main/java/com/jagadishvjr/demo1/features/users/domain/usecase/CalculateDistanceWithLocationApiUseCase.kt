package com.jagadishvjr.demo1.features.users.domain.usecase

import android.location.Location
import com.jagadishvjr.demo1.features.users.domain.model.Coordinates
import javax.inject.Inject
import kotlin.math.round

class CalculateDistanceWithLocationApiUseCase @Inject constructor() : DistanceCalculatorUseCase {

    override operator fun invoke(from: Coordinates, to: Coordinates): Double {
        println("formatted distance invoke ENTRY")
        val results = FloatArray(1)
        Location.distanceBetween(
            from.latitude,
            from.longitude,
            to.latitude,
            to.longitude,
            results
        )

        val distanceInKm = results.first().toDouble() / 1000
        val formatted = "%.2f".format(distanceInKm)
        println("formatted distance is $formatted")
        return round(distanceInKm * 100) / 100
    }
}
