package com.jagadishvjr.demo1.features.fixtures.domain.usecase

import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.fixtures.domain.model.Fixture
import com.jagadishvjr.demo1.features.fixtures.domain.repository.FixtureRepository
import javax.inject.Inject

class GetFixturesUseCase @Inject constructor(
    private val fixtureRepository: FixtureRepository
) {

    suspend operator fun invoke(): AppResult<List<Fixture>> {
        return when (val result = fixtureRepository.getFixtures()) {
            is AppResult.Success -> {
                val filteredFixtures = result.data.filter { it.name.isNotBlank() }
                AppResult.Success(filteredFixtures)
            }
            is AppResult.Error -> result
        }
    }
}
