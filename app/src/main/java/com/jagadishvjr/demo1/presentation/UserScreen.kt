package com.jagadishvjr.demo1.presentation

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(viewModel: FixtureViewModel = hiltViewModel()){

    LaunchedEffect(Unit) {
        viewModel.fetchFixtures()
    }
    val state = viewModel.uiState.collectAsState().value

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBar(title = {
                Text("Sample Demo")
            }) }
        ) { paddingValues ->
            when(state){
                is FixtureListUiState.Loading ->{
                    CircularProgressIndicator()
                }
                is FixtureListUiState.Empty ->{
                    MesssageContent("No data")
                }
                is FixtureListUiState.Error ->{
                    MesssageContent(state.message)
                }
                is FixtureListUiState.Success ->{
                    val data = state.list
                    LazyColumn(
                        modifier = Modifier.padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        items(
                            items = data,
                            key = {it.id}
                        ){ fixture ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(10.dp)
                            ) {
                                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                    Text(text = fixture.name)
                                    Spacer(modifier = Modifier.fillMaxWidth().padding(10.dp))
                                    Text(text = fixture.resultInfo)
                                }
                            }

                        }

                    }
                }
            }
        }
    }

}


@Composable
fun MesssageContent(content: String){
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = content)
        }
    }
}
