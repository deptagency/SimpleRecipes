package com.template2024.data.repositories

import com.template2024.data.sources.local.Database
import com.template2024.data.sources.remote.api.API
import com.template2024.data.sources.remote.dto.response.CategoryResponse
import com.template2024.data.sources.remote.dto.response.toMeal
import com.template2024.domain.models.Meal
import com.template2024.domain.repositories.RecipesRepository

class RecipesRepositoryImpl(
    private val api: API,
    private val database: Database
) : RecipesRepository {
    override suspend fun getCategories(): Result<List<CategoryResponse>> {
        return try {
            val categoriesResponse = api.getCategories()

            Result.success(categoriesResponse.categories)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun getMealsByCategory(category: String): Result<List<Meal>> {
        return try {
            val categoryMealsResponse = api.getCategoryMealsList(category)

            Result.success(
                categoryMealsResponse.mealsList.map {
                    it.toMeal()
                }
            )
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}