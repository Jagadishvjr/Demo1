package com.jagadishvjr.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.Locale

@Composable
fun UserRoute(viewModel: UserViewModel = hiltViewModel()){
    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }
    val state = viewModel.uiState.collectAsState()
    UserScreen(state = state.value)
}



@Composable
fun UserScreen(state: UserUiState){

    when(state){

        is UserUiState.Loading ->{
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("loading_indicator"))
            }
        }
        is UserUiState.Success ->{
            val users = (state as UserUiState.Success).list

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(top = 60.dp, bottom = 16.dp)
            ) {
                items(users){ user ->

                    Card(
                        modifier = Modifier.fillMaxWidth().padding(5.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(10.dp)
                        ) {
                            Text(text = user.name)
                            Spacer(modifier = Modifier.fillMaxWidth().padding(6.dp))
                            Text(text = user.address.city)

                            Spacer(modifier = Modifier.fillMaxWidth().padding(6.dp))
                            Text(text = user.address.zipcode)

                            user.distanceFromCurrentUserKm?.let { distance ->
                                Spacer(modifier = Modifier.fillMaxWidth().padding(6.dp))
                                Text(
                                    text = "Distance: ${"%.2f".format(Locale.US, distance)} km"
                                )
                            }
                        }

                    }

                }
            }

        }
        is UserUiState.Error -> {
            Text(
                modifier = Modifier.testTag("error_message"),
                text = state.message
            )
        }
    }
}
