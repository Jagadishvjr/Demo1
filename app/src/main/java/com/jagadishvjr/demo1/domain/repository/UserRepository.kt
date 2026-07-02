package com.jagadishvjr.demo1.domain.repository

import com.jagadishvjr.demo1.common.AppResult
import com.jagadishvjr.demo1.domain.model.Fixure

interface UserRepository{
    suspend fun getFixtures(): AppResult<List<Fixure>>
}