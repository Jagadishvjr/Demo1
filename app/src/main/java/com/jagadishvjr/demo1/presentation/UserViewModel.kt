package com.jagadishvjr.demo1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagadishvjr.demo1.core.AppResult
import com.jagadishvjr.demo1.domain.model.UserItem
import com.jagadishvjr.demo1.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UserUiState{
    data object Loading: UserUiState
    data object Empty: UserUiState
    data class Success(val data: List<UserItem>): UserUiState
    data class Error(val message: String ): UserUiState
}

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ViewModel(){


    private val _uiState : MutableStateFlow<UserUiState> = MutableStateFlow(UserUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchUsers()
    }


    fun fetchUsers(){
        //if(uiState.value == UserUiState.Loading) return

        viewModelScope.launch {
            _uiState.value = UserUiState.Loading

            when(val response = getUserUseCase()){
                is AppResult.Empty -> {
                    _uiState.value = UserUiState.Empty
                }
                is AppResult.Loading -> {
                    _uiState.value = UserUiState.Loading
                }
                is AppResult.Error -> {
                    _uiState.value = UserUiState.Error(response.message)
                }

                is AppResult.Success -> {
                    _uiState.value = UserUiState.Success(response.data)
                }


            }
        }


    }



}