package com.nomaan.simplerecipes.ui.screens.savedmealslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nomaan.simplerecipes.domain.models.MealInfo
import com.nomaan.simplerecipes.domain.usecases.GetSavedMealsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SavedMealsListViewModel (
    private val getSavedMealsUseCase: GetSavedMealsUseCase
) : ViewModel() {
    sealed class SavedMealsListUiState {
        data class Idle(val meals: List<MealInfo>) : SavedMealsListUiState()
        data object Loading : SavedMealsListUiState()
        data class Error(val errorMessage: String): SavedMealsListUiState()
    }

    private val _savedMealsListUiState = MutableStateFlow<SavedMealsListUiState>(
        SavedMealsListUiState.Idle(emptyList())
    )
    val savedMealsListUiState: StateFlow<SavedMealsListUiState> = _savedMealsListUiState

    init {
        getSavedMeals()
    }

    private fun getSavedMeals() {
        viewModelScope.launch {
            _savedMealsListUiState.emit(SavedMealsListUiState.Loading)

            getSavedMealsUseCase().collectLatest { meals ->
                if (meals.isEmpty()) {
                    _savedMealsListUiState.emit(SavedMealsListUiState.Error("No saved meals."))
                } else {
                    _savedMealsListUiState.emit(SavedMealsListUiState.Idle(meals))
                }
            }
        }
    }
}