package com.jagadishvjr.demo1.domain.usecase

import com.jagadishvjr.demo1.core.AppResult
import com.jagadishvjr.demo1.domain.model.UserItem
import com.jagadishvjr.demo1.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){

    suspend operator fun invoke(): AppResult<List<UserItem>>{
        return  userRepository.getUsers()
    }

}