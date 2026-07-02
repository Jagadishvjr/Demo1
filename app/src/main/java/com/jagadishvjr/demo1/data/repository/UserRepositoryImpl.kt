package com.jagadishvjr.demo1.data.repository

import com.jagadishvjr.demo1.common.AppResult
import com.jagadishvjr.demo1.data.mapper.toDomian
import com.jagadishvjr.demo1.data.remote.api.UserApiService
import com.jagadishvjr.demo1.domain.model.Fixure
import com.jagadishvjr.demo1.domain.repository.UserRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
): UserRepository{

    override suspend fun getFixtures(): AppResult<List<Fixure>> {
        return try {
            val result = apiService.getUsers()
            AppResult.Success(result.data.map { it.toDomian() })
        }catch (e : CancellationException){
            throw e
        }catch (e : IOException){
            AppResult.Error("Please check the internet conection")
        }catch (e : HttpException){
            AppResult.Error("Server Error occured")
        }catch (e : Exception){
            AppResult.Error("Something went wrong")
        }
    }
}