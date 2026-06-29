package com.jagadishvjr.demo1.domin.usecase

import com.jagadishvjr.demo1.domin.model.User
import com.jagadishvjr.demo1.domin.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(): List<User>{
        val users = repository.getUsers()
        //return users
        return users.filter { user ->
            user.name.startsWith('A', ignoreCase = true) ||
                    user.name.startsWith('E', ignoreCase = true) ||
                    user.name.startsWith('I', ignoreCase = true) ||
                    user.name.startsWith('O', ignoreCase = true) ||
                    user.name.startsWith('U', ignoreCase = true)
        }
    }
}