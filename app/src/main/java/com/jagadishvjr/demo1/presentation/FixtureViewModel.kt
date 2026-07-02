package com.jagadishvjr.demo1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jagadishvjr.demo1.common.AppResult
import com.jagadishvjr.demo1.domain.model.Fixure
import com.jagadishvjr.demo1.domain.usecase.GetFixureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FixtureListUiState{
    data object Loading : FixtureListUiState
    data object Empty : FixtureListUiState
    data class Success(val list: List<Fixure>): FixtureListUiState
    data class Error(val message: String): FixtureListUiState
}

@HiltViewModel
class FixtureViewModel @Inject constructor(
    private val getFixureUseCase: GetFixureUseCase
): ViewModel(){

    private val _uiState: MutableStateFlow<FixtureListUiState> = MutableStateFlow(FixtureListUiState.Loading)
    val uiState  = _uiState.asStateFlow()

    fun fetchFixtures(){
        viewModelScope.launch {
            when(val useCase = getFixureUseCase()){
                is AppResult.Success -> {
                    _uiState.value = if (useCase.data.isEmpty()) {
                        FixtureListUiState.Empty
                    } else {
                        FixtureListUiState.Success(useCase.data)
                    }
                }
                is AppResult.Error -> {
                    _uiState.value = FixtureListUiState.Error(useCase.message)
                }
            }

        }
    }

}
