package com.jagadishvjr.demo1.core.location

import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.users.domain.model.Coordinates

interface LocationTracker {
    suspend fun getCurrentLocation(): AppResult<Coordinates>
}
