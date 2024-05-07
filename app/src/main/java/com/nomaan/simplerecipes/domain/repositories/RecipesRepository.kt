package com.nomaan.simplerecipes.domain.repositories

import com.nomaan.simplerecipes.domain.models.Category
import com.nomaan.simplerecipes.domain.models.Meal
import com.nomaan.simplerecipes.domain.models.MealInfo
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    suspend fun getCategories(): Result<List<Category>>
    suspend fun getMealsByCategory(category: String): Result<List<Meal>>
    suspend fun getMealDetails(mealId: String): Result<MealInfo>
    suspend fun getAllSavedMeals(): Flow<List<MealInfo>>
    suspend fun saveMealDetails(mealInfo: MealInfo): Result<Boolean>
    suspend fun deleteMealDetails(mealId: String): Result<Boolean>
}