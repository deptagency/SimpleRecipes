package com.template2024.domain.repositories

import com.template2024.data.sources.remote.dto.response.CategoryResponse
import com.template2024.domain.models.Meal

interface RecipesRepository {
    suspend fun getCategories(): Result<List<CategoryResponse>>
    suspend fun getMealsByCategory(category: String): Result<List<Meal>>
}