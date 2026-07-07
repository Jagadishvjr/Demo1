package com.jagadishvjr.demo1.features.users.domain.repository

import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.users.domain.model.User

interface UserRepository {
    suspend fun getUsers(): AppResult<List<User>>
}
