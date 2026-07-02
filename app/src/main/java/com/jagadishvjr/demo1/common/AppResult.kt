package com.jagadishvjr.demo1.common

import com.jagadishvjr.demo1.domain.model.Fixure

sealed interface AppResult<out T>{
    data class Success<T>(val data: T): AppResult<T>
    data class Error(val message: String, val cause: Throwable? = null): AppResult<Nothing>
}