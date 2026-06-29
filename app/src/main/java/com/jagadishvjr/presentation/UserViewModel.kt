package com.jagadishvjr.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagadishvjr.demo1.domin.model.User
import com.jagadishvjr.demo1.domin.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UserUiState{
    object Loading : UserUiState()
    data class Success(val list: List<User>): UserUiState()
    data class Error(val message: String): UserUiState()
}

@HiltViewModel
class UserViewModel @Inject constructor(
    private val useCase: GetUserUseCase
): ViewModel(){

    private val _uiState : MutableStateFlow<UserUiState> = MutableStateFlow(UserUiState.Loading)
    val uiState : StateFlow<UserUiState> = _uiState.asStateFlow()



    fun fetchUsers(){
        _uiState.value = UserUiState.Loading

        viewModelScope.launch {
            try {
                val users = useCase.invoke()
                _uiState.value = UserUiState.Success(users)
            }catch (e : Exception){
                _uiState.value = UserUiState.Error(e.message ?: "Error")
            }


        }

    }


}