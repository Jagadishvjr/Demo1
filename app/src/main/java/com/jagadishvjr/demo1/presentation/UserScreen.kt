package com.jagadishvjr.demo1.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun UserScreen(viewModel: UserViewModel = hiltViewModel()){

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    when(val currentState = state.value){
        is UserUiState.Loading -> {
            CircularProgressIndicator()
        }
        is UserUiState.Empty -> {
            Text("No data")
        }
        is UserUiState.Error -> {
            Text(currentState.message)
        }
        is UserUiState.Success -> {
            val users = currentState.data

            LazyColumn() {
                items(
                    items = users,
                    key = { it.id }
                ){ user ->

                    Text(user.name + " " + user.email)

                }
            }
        }
    }

}
