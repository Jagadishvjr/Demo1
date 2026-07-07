package com.jagadishvjr.demo1.features.fixtures.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagadishvjr.demo1.core.result.AppResult
import com.jagadishvjr.demo1.features.fixtures.domain.model.Fixture
import com.jagadishvjr.demo1.features.fixtures.domain.usecase.GetFixturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface FixturesUiState {
    data object Loading : FixturesUiState
    data object Empty : FixturesUiState
    data class Success(val fixtures: List<Fixture>) : FixturesUiState
    data class Error(val message: String) : FixturesUiState
}

@HiltViewModel
class FixturesViewModel @Inject constructor(
    private val getFixturesUseCase: GetFixturesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FixturesUiState>(FixturesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchFixtures() {
        _uiState.value = FixturesUiState.Loading
        viewModelScope.launch {
            when (val result = getFixturesUseCase()) {
                is AppResult.Success -> {
                    _uiState.value = if (result.data.isEmpty()) {
                        FixturesUiState.Empty
                    } else {
                        FixturesUiState.Success(result.data)
                    }
                }
                is AppResult.Error -> {
                    _uiState.value = FixturesUiState.Error(result.message)
                }
            }
        }
    }
}
