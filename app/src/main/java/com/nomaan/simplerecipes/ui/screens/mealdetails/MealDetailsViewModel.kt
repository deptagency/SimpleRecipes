package com.nomaan.simplerecipes.ui.screens.mealdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nomaan.simplerecipes.domain.models.MealInfo
import com.nomaan.simplerecipes.domain.usecases.DeleteMealDetailsUseCase
import com.nomaan.simplerecipes.domain.usecases.GetMealDetailsUseCase
import com.nomaan.simplerecipes.domain.usecases.SaveMealDetailsUseCase
import com.nomaan.simplerecipes.ui.navigation.ParameterNames
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealDetailsViewModel(
    private val getMealDetailsUseCase: GetMealDetailsUseCase,
    private val saveMealDetailsUseCase: SaveMealDetailsUseCase,
    private val deleteMealDetailsUseCase: DeleteMealDetailsUseCase,
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
            getMealDetails()
        }
    }

    private suspend fun getMealDetails() {
        delay(300)
        _mealDetailsUiState.emit(MealDetailsUiState.Loading)

        val mealInfo = getMealDetailsUseCase(mealId).getOrNull()

        if (mealInfo != null) {
            _mealDetailsUiState.emit(MealDetailsUiState.Idle(mealInfo))
        } else {
            _mealDetailsUiState.emit(MealDetailsUiState.Error("Unable to fetch meal details."))
        }
    }

    fun saveOrDeleteMealDetails() {
        viewModelScope.launch {
            val mealInfo = (_mealDetailsUiState.value as MealDetailsUiState.Idle).mealInfo
            mealInfo?.let { info ->
                val result = if (!info.savedToDB) {
                    saveMealDetailsUseCase(info).getOrElse { false }
                } else {
                    deleteMealDetailsUseCase(info.mealId).getOrElse { false }
                }
                if (result) {
                    getMealDetails()
                }
            }
        }
    }
}