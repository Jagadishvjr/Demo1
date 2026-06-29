package com.jagadishvjr.demo1.data.repository

import com.jagadishvjr.demo1.data.mapper.toDomain
import com.jagadishvjr.demo1.data.remote.UserApiService
import com.jagadishvjr.demo1.domin.model.User
import com.jagadishvjr.demo1.domin.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
): UserRepository{

    override suspend fun getUsers(): List<User> {
        return userApiService.getUsers().map { it.toDomain() }
    }
}