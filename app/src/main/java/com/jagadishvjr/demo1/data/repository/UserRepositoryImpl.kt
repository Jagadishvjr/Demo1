package com.jagadishvjr.demo1.data.repository

import com.jagadishvjr.demo1.core.AppResult
import com.jagadishvjr.demo1.data.mapper.toDomain
import com.jagadishvjr.demo1.data.remote.api.UserAPiService
import com.jagadishvjr.demo1.domain.model.UserItem
import com.jagadishvjr.demo1.domain.repository.UserRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class UserRepositoryImpl @Inject constructor(
    private val userAPiService: UserAPiService
): UserRepository{
    override suspend fun getUsers(): AppResult<List<UserItem>> {
        println("UserRepositoryImpl Entry")
        return try {
            val users = userAPiService.getUsers()
            println("UserRepositoryImpl users $users")
            if(users.isEmpty()){
                AppResult.Empty
            }else{
                AppResult.Success(users.map { it.toDomain() })
            }
        }catch (e: CancellationException){
            throw e
        }catch (e: IOException){
            println("Errr: $e")
            AppResult.Error("Please check your internet")
        }catch (e: HttpException){
            AppResult.Error("Server Error")
        }catch (e: Exception){
            AppResult.Error("Something went wrong")
        }

    }
}