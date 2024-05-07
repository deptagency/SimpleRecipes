package com.nomaan.simplerecipes.domain.usecases

import com.nomaan.simplerecipes.domain.repositories.RecipesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteMealDetailsUseCase (
    private val recipesRepository: RecipesRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(mealId: String) = withContext(defaultDispatcher) {
        recipesRepository.deleteMealDetails(mealId)
    }
}