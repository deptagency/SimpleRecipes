package com.template2024.domain.repositories

import com.template2024.data.sources.remote.dto.response.CategoryResponse
import com.template2024.domain.models.Meal
import com.template2024.domain.models.MealInfo

interface RecipesRepository {
    suspend fun getCategories(): Result<List<CategoryResponse>>
    suspend fun getMealsByCategory(category: String): Result<List<Meal>>
    suspend fun getMealDetails(mealId: String): Result<MealInfo>
    suspend fun saveMealDetails(mealInfo: MealInfo): Result<Boolean>
    suspend fun deleteMealDetails(mealId: String): Result<Boolean>
}