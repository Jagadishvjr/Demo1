package com.jagadishvjr.demo1.features.users.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagadishvjr.demo1.core.location.LocationTracker
import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.users.domain.model.Coordinates
import com.jagadishvjr.demo1.features.users.domain.model.User
import com.jagadishvjr.demo1.features.users.domain.usecase.DistanceCalculatorUseCase
import com.jagadishvjr.demo1.features.users.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Named
import javax.inject.Inject

sealed interface UsersUiState {
    data object Loading : UsersUiState
    data object Empty : UsersUiState
    data class Success(
        val users: List<UserCardUiModel>,
        val infoMessage: String? = null
    ) : UsersUiState
    data class Error(val message: String) : UsersUiState
}

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val locationTracker: LocationTracker,
    @Named("haversineDistance")
    private val calculateDistanceUseCase: DistanceCalculatorUseCase,
    @Named("locationApiDistance")
    private val calculateDistanceWithLocationApiUseCase: DistanceCalculatorUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchUsers(shouldLoadDistance: Boolean) {
        _uiState.value = UsersUiState.Loading
        viewModelScope.launch {
            when (val result = getUsersUseCase()) {
                is AppResult.Success -> {
                    _uiState.value = if (result.data.isEmpty()) {
                        UsersUiState.Empty
                    } else {
                        if (shouldLoadDistance) {
                            mapUsersWithDistance(result.data)
                        } else {
                            UsersUiState.Success(
                                users = result.data.map { user ->
                                    user.toUiModel(distanceKm = null)
                                },
                                infoMessage = "Location permission denied. Showing users without distance."
                            )
                        }
                    }
                }
                is AppResult.Error -> {
                    _uiState.value = UsersUiState.Error(result.message)
                }
            }
        }
    }

    private suspend fun mapUsersWithDistance(
        users: List<User>
    ): UsersUiState {
        return when (val locationResult = locationTracker.getCurrentLocation()) {
            is AppResult.Success -> {
                UsersUiState.Success(
                    users = users.map { user ->
                        val destination = Coordinates(
                            latitude = user.geo.latitude,
                            longitude = user.geo.longitude
                        )
                        user.toUiModel(
                            distanceKm = calculateDistance(locationResult.data, destination)
                        )
                    }
                )
            }
            is AppResult.Error -> {
                UsersUiState.Success(
                    users = users.map { user ->
                        user.toUiModel(distanceKm = null)
                    },
                    infoMessage = "${locationResult.message}. Showing users without distance."
                )
            }
        }
    }

    private fun calculateDistance(
        from: Coordinates,
        to: Coordinates
    ): Double {
        return runCatching {
            calculateDistanceWithLocationApiUseCase(from, to)
        }.getOrElse {
            calculateDistanceUseCase(from, to)
        }
    }
}

private fun User.toUiModel(distanceKm: Double?): UserCardUiModel {
    return UserCardUiModel(
        id = id,
        name = name,
        address = address.formatted(),
        latitude = geo.latitude,
        longitude = geo.longitude,
        distanceKm = distanceKm
    )
}
