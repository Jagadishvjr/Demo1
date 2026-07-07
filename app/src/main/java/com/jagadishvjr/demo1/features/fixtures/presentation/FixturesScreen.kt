package com.jagadishvjr.demo1.features.fixtures.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jagadishvjr.demo1.R
import com.jagadishvjr.demo1.features.fixtures.domain.model.Fixture

@Composable
fun FixturesRoute(
    paddingValues: PaddingValues,
    viewModel: FixturesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchFixtures()
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    FixturesScreen(
        state = state.value,
        paddingValues = paddingValues
    )
}

@Composable
fun FixturesScreen(
    state: FixturesUiState,
    paddingValues: PaddingValues
) {
    when (state) {
        is FixturesUiState.Loading -> CenteredFixtureContent(paddingValues) {
            CircularProgressIndicator()
        }
        is FixturesUiState.Empty -> CenteredFixtureContent(paddingValues) {
            Text(text = stringResource(R.string.fixtures_empty_message))
        }
        is FixturesUiState.Error -> CenteredFixtureContent(paddingValues) {
            Text(text = state.message)
        }
        is FixturesUiState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(
                    items = state.fixtures,
                    key = { it.id }
                ) { fixture ->
                    FixtureCard(
                        fixture = fixture,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FixtureCard(
    fixture: Fixture,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = fixture.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = fixture.resultInfo,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun CenteredFixtureContent(
    paddingValues: PaddingValues,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
