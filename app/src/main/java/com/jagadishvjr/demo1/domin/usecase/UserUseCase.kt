package com.jagadishvjr.demo1.domin.usecase

import android.location.Location
import com.jagadishvjr.demo1.domin.model.Geo
import com.jagadishvjr.demo1.domin.model.User
import com.jagadishvjr.demo1.domin.repository.UserRepository
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(): List<User>{
        val users = repository.getUsers()
//        return users.map { user ->
//            user.copy(
//                distanceFromCurrentUserKm = locationDistance(
//                    from = currentUserLocation,
//                    to = user.address.geo
//                )
//            )
//        }
        return users.filter { user ->
            user.name.startsWith('A', ignoreCase = true) ||
                    user.name.startsWith('E', ignoreCase = true) ||
                    user.name.startsWith('I', ignoreCase = true) ||
                    user.name.startsWith('O', ignoreCase = true) ||
                    user.name.startsWith('U', ignoreCase = true)
        }.map { user ->
            user.copy(
                distanceFromCurrentUserKm = calculateDistanceInKm(
                    from = currentUserLocation,
                    to = user.address.geo
                )
            )
        }
    }

    private fun locationDistance(from: Geo, to: Geo): Double{
        val results = FloatArray(1)
        Location.distanceBetween(
            from.lat,
            from.lng,
            to.lat,
            to.lng,
            results
        )
        return results[0].toDouble() / 1000
    }

    private fun calculateDistanceInKm(from: Geo, to: Geo): Double {
        val earthRadiusInKm = 6371.0
        val latDistance = Math.toRadians(to.lat - from.lat)
        val lngDistance = Math.toRadians(to.lng - from.lng)
        val startLat = Math.toRadians(from.lat)
        val endLat = Math.toRadians(to.lat)

        val haversineValue = sin(latDistance / 2).pow(2) +
                cos(startLat) * cos(endLat) * sin(lngDistance / 2).pow(2)

        return 2 * earthRadiusInKm * asin(sqrt(haversineValue))
    }

    private companion object {
        val currentUserLocation = Geo(//27.146212967448196, 17.311895591346307
            lat = 27.14,
            lng = 17.31
        )
    }
}
