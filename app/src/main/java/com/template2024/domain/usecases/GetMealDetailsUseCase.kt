package com.template2024.domain.usecases

import com.template2024.domain.repositories.RecipesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMealDetailsUseCase (
    private val recipesRepository: RecipesRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(mealId: String) = withContext(defaultDispatcher) {
        recipesRepository.getMealDetails(mealId)
    }
}