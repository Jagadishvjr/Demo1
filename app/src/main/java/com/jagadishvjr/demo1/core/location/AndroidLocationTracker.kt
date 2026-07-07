package com.jagadishvjr.demo1.core.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.CancellationSignal
import androidx.core.content.ContextCompat
import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.users.domain.model.Coordinates
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class AndroidLocationTracker @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationTracker {

    private val locationManager: LocationManager
        get() = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    override suspend fun getCurrentLocation(): AppResult<Coordinates> {
        if (!hasLocationPermission()) {
            return AppResult.Error("Location permission not granted")
        }

        val provider = when {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> {
                LocationManager.GPS_PROVIDER
            }
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> {
                LocationManager.NETWORK_PROVIDER
            }
            else -> null
        } ?: return AppResult.Error("Location service is disabled")

        return requestCurrentLocation(provider)
    }

    private fun hasLocationPermission(): Boolean {
        val finePermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarsePermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return finePermission || coarsePermission
    }

    @SuppressLint("MissingPermission")
    private suspend fun requestCurrentLocation(provider: String): AppResult<Coordinates> {
        return suspendCancellableCoroutine { continuation ->
            val cancellationSignal = CancellationSignal()
            continuation.invokeOnCancellation {
                cancellationSignal.cancel()
            }

            locationManager.getCurrentLocation(
                provider,
                cancellationSignal,
                ContextCompat.getMainExecutor(context)
            ) { location ->
                val result = if (location != null) {
                    AppResult.Success(
                        Coordinates(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                } else {
                    AppResult.Error("Unable to read current location")
                }
                continuation.resume(result)
            }
        }
    }
}
