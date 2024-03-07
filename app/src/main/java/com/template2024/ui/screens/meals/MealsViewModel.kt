package com.template2024.ui.screens.meals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template2024.data.repositories.DataStoreRepository
import com.template2024.domain.models.Meal
import com.template2024.domain.usecases.GetMealsByCategoryUseCase
import com.template2024.ui.navigation.ParameterNames
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MealsViewModel(
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    private val dataStoreRepository: DataStoreRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val category: String = checkNotNull(savedStateHandle[ParameterNames.CATEGORY_NAME])

    sealed class MealsListUiState {
        data class Idle(val meals: List<Meal>) : MealsListUiState()
        data object Loading : MealsListUiState()
        data class Error(val errorMessage: String) : MealsListUiState()
    }

    private val _mealsListUiState =
        MutableStateFlow<MealsListUiState>(MealsListUiState.Idle(emptyList()))
    val mealsListUiState: StateFlow<MealsListUiState> = _mealsListUiState

    private val _showInformationOverlay = MutableStateFlow(false)
    val showInformationOverlay: StateFlow<Boolean> = _showInformationOverlay

    init {
        viewModelScope.launch {
            if (category.isNotBlank()) {
                getMeals()
            } else {
                _mealsListUiState.emit(MealsListUiState.Error("Unable to fetch meals."))
            }
        }
    }

    private suspend fun getMeals() {
        delay(300)
        _mealsListUiState.emit(MealsListUiState.Loading)

        val meals = getMealsByCategoryUseCase(category).getOrNull()

        val firstTenMeals = meals?.take(10)

        if (firstTenMeals?.isNotEmpty() == true) {
            _mealsListUiState.emit(MealsListUiState.Idle(firstTenMeals))
            checkShowInformationOverlay()
        } else {
            _mealsListUiState.emit(MealsListUiState.Error("Unable to fetch meals."))
        }
    }

    private suspend fun checkShowInformationOverlay() {
        dataStoreRepository.showMealsInformationOverlayFlow.collectLatest { shouldShow ->
            _showInformationOverlay.emit(shouldShow)
        }
    }

    fun dismissMealsInformationOverlayClicked() {
        viewModelScope.launch {
            dataStoreRepository.setShowMealsInformationOverlayValueFalse()
            _showInformationOverlay.emit(false)
        }
    }
}