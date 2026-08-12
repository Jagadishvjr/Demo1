package com.jagadishvjr.demo1.core

import com.jagadishvjr.demo1.data.remote.dto.UserDto
import com.jagadishvjr.demo1.domain.model.User
import com.jagadishvjr.demo1.domain.model.UserItem

sealed interface AppResult<out T>{
    data object Loading : AppResult<Nothing>
    data object Empty : AppResult<Nothing>
    data class Success<T>(val data: List<UserItem>): AppResult<T>
    data class Error(val message: String, val cause: Throwable? = null): AppResult<Nothing>
}