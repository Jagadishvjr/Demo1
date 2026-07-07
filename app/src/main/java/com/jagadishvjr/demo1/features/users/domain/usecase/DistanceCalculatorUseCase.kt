package com.jagadishvjr.demo1.features.users.domain.usecase

import com.jagadishvjr.demo1.features.users.domain.model.Coordinates

interface DistanceCalculatorUseCase {
    operator fun invoke(from: Coordinates, to: Coordinates): Double
}
