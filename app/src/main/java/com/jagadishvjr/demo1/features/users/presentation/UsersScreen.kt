package com.jagadishvjr.demo1.features.users.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jagadishvjr.demo1.R

@Composable
fun UsersRoute(
    paddingValues: PaddingValues,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.fetchUsers(shouldLoadDistance = isGranted)
    }

    LaunchedEffect(Unit) {
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        viewModel.fetchUsers(shouldLoadDistance = isGranted)
        if (!isGranted) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    UsersScreen(
        state = state.value,
        paddingValues = paddingValues
    )
}

@Composable
fun UsersScreen(
    state: UsersUiState,
    paddingValues: PaddingValues
) {
    when (state) {
        is UsersUiState.Loading -> CenteredMessage(
            paddingValues = paddingValues
        ) {
            CircularProgressIndicator()
        }

        is UsersUiState.Empty -> CenteredMessage(
            paddingValues = paddingValues
        ) {
            Text(text = stringResource(R.string.users_empty_message))
        }

        is UsersUiState.Error -> CenteredMessage(
            paddingValues = paddingValues
        ) {
            Text(text = state.message)
        }

        is UsersUiState.Success -> UsersList(
            users = state.users,
            infoMessage = state.infoMessage,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

@Composable
private fun UsersList(
    users: List<UserCardUiModel>,
    infoMessage: String?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            if (infoMessage != null) {
                Text(
                    text = infoMessage,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        items(
            items = users,
            key = { it.id }
        ) { user ->
            UserCard(
                user = user,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun UserCard(
    user: UserCardUiModel,
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
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = stringResource(R.string.address_label, user.address),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(
                    R.string.geo_label,
                    user.latitude,
                    user.longitude
                ),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = user.distanceKm?.let {
                    stringResource(R.string.distance_label, it)
                } ?: stringResource(R.string.distance_unavailable_label),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CenteredMessage(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
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
