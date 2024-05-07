package com.nomaan.simplerecipes.domain.usecases

import com.nomaan.simplerecipes.domain.models.MealInfo
import com.nomaan.simplerecipes.domain.repositories.RecipesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveMealDetailsUseCase (
    private val recipesRepository: RecipesRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(mealInfo: MealInfo) = withContext(defaultDispatcher) {
        recipesRepository.saveMealDetails(mealInfo)
    }
}