package com.nomaan.simplerecipes.domain.usecases

import com.nomaan.simplerecipes.domain.repositories.RecipesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMealsByCategoryUseCase (
    private val recipesRepository: RecipesRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(category: String) = withContext(defaultDispatcher) {
        recipesRepository.getMealsByCategory(category)
    }
}