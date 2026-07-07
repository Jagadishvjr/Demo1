package com.jagadishvjr.demo1.features.fixtures.data.repository

import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.fixtures.data.mapper.toDomain
import com.jagadishvjr.demo1.features.fixtures.data.remote.api.FixtureApiService
import com.jagadishvjr.demo1.features.fixtures.domain.model.Fixture
import com.jagadishvjr.demo1.features.fixtures.domain.repository.FixtureRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class FixtureRepositoryImpl @Inject constructor(
    private val fixtureApiService: FixtureApiService
) : FixtureRepository {

    override suspend fun getFixtures(): AppResult<List<Fixture>> {
        return try {
            val result = fixtureApiService.getFixtures()
            AppResult.Success(result.data.map { it.toDomain() })
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: IOException) {
            AppResult.Error("Network error", exception)
        } catch (exception: HttpException) {
            AppResult.Error("Server error", exception)
        } catch (exception: Exception) {
            AppResult.Error("Something went wrong", exception)
        }
    }
}
