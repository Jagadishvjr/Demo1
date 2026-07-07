package com.jagadishvjr.demo1.features.fixtures.domain.repository

import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.fixtures.domain.model.Fixture

interface FixtureRepository {
    suspend fun getFixtures(): AppResult<List<Fixture>>
}
