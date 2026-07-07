package com.jagadishvjr.demo1.features.users.domain.usecase

import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.users.domain.model.User
import com.jagadishvjr.demo1.features.users.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): AppResult<List<User>> {
        return when (val result = userRepository.getUsers()) {
            is AppResult.Success -> {
                val filteredUsers = result.data
                    .filter { it.name.isNotBlank() }
                    .sortedBy { it.name.lowercase() }
                AppResult.Success(filteredUsers)
            }
            is AppResult.Error -> result
        }
    }
}
