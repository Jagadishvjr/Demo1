package com.jagadishvjr.demo1.domin.repository

import com.jagadishvjr.demo1.domin.model.User

interface UserRepository {
    suspend fun getUsers() : List<User>
}