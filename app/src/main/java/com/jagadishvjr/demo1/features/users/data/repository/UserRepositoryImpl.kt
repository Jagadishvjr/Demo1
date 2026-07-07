package com.jagadishvjr.demo1.features.users.data.repository

import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.users.data.mapper.toDomain
import com.jagadishvjr.demo1.features.users.data.remote.api.UserApiService
import com.jagadishvjr.demo1.features.users.domain.model.User
import com.jagadishvjr.demo1.features.users.domain.repository.UserRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserRepository {

    override suspend fun getUsers(): AppResult<List<User>> {

        return runCatching {
            userApiService.getUsers()
        }.fold(
            onSuccess = { users ->
                AppResult.Success(users.map { it.toDomain() })
            },
            onFailure = { exception ->
                when(exception){
                    is CancellationException -> throw exception
                    is java.io.IOException -> AppResult.Error("Network error", exception)
                    is HttpException -> AppResult.Error("Server error", exception)
                    else -> AppResult.Error("Something went wrong error", exception)
                }

            }
        )
//        return try {
//            val users = userApiService.getUsers()
//            AppResult.Success(users.map { it.toDomain() })
//        } catch (exception: CancellationException) {
//            throw exception
//        } catch (exception: IOException) {
//            AppResult.Error("Network error", exception)
//        } catch (exception: HttpException) {
//            AppResult.Error("Server error", exception)
//        } catch (exception: Exception) {
//            AppResult.Error("Something went wrong", exception)
//        }
    }
}
