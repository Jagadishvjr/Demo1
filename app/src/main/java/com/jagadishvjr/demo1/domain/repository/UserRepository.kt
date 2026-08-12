package com.jagadishvjr.demo1.domain.repository

import com.jagadishvjr.demo1.core.AppResult
import com.jagadishvjr.demo1.domain.model.User
import com.jagadishvjr.demo1.domain.model.UserItem

interface UserRepository {
    suspend fun getUsers(): AppResult<List<UserItem>>
}