package com.template2024.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template2024.data.sources.remote.dto.response.CategoryResponse
import com.template2024.domain.usecases.GetCategoriesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel (
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {
    sealed class HomeUiState {
        data class Idle(val categories: List<CategoryResponse>) : HomeUiState()
        data object Loading : HomeUiState()
        data class Error(val errorMessage: String): HomeUiState()
    }

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle(emptyList()))
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _homeUiState.emit(HomeUiState.Loading)
            delay(1000) // Fake network delay

            val categories = getCategoriesUseCase().getOrNull()

            if (categories?.isNotEmpty() == true) {
                _homeUiState.emit(HomeUiState.Idle(categories))
            } else {
                _homeUiState.emit(HomeUiState.Error("Unable to fetch categories."))
            }
        }
    }
}