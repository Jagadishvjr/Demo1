package com.jagadishvjr.demo1.domain.usecase

import com.jagadishvjr.demo1.common.AppResult
import com.jagadishvjr.demo1.domain.model.Fixure
import com.jagadishvjr.demo1.domain.repository.UserRepository
import javax.inject.Inject

class GetFixureUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(): AppResult<List<Fixure>>{

        return when (val result = repository.getFixtures()){
            is AppResult.Error -> result
            is AppResult.Success -> {
                val fixtures = result.data
                    .filter { it.name.isNotBlank() }
                    .sortedBy { it.name.lowercase() }
                AppResult.Success(fixtures)
            }
        }
    }
}
