package com.template2024.ui.screens.mealdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.template2024.domain.models.MealInfo
import com.template2024.domain.usecases.GetMealDetailsUseCase
import com.template2024.ui.navigation.ParameterNames
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val getMealDetailsUseCase: GetMealDetailsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val mealId: String = checkNotNull(savedStateHandle[ParameterNames.MEAL_ID])

    sealed class MealDetailsUiState {
        data class Idle(val mealInfo: MealInfo?) : MealDetailsUiState()
        data object Loading : MealDetailsUiState()
        data class Error(val errorMessage: String) : MealDetailsUiState()
    }

    private val _mealDetailsUiState =
        MutableStateFlow<MealDetailsUiState>(MealDetailsUiState.Idle(null))
    val mealDetailsUiState: StateFlow<MealDetailsUiState> = _mealDetailsUiState

    init {
        viewModelScope.launch {
            getMeals()
        }
    }

    private suspend fun getMeals() {
        delay(300)
        _mealDetailsUiState.emit(MealDetailsUiState.Loading)

        val mealInfo = getMealDetailsUseCase(mealId).getOrNull()

        if (mealInfo != null) {
            _mealDetailsUiState.emit(MealDetailsUiState.Idle(mealInfo))
        } else {
            _mealDetailsUiState.emit(MealDetailsUiState.Error("Unable to fetch meal details."))
        }
    }
}