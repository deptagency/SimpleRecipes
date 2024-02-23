package com.template2024.data.repositories

import com.template2024.data.sources.local.Database
import com.template2024.data.sources.remote.api.API
import com.template2024.data.sources.remote.dto.response.CategoryResponse
import com.template2024.domain.repositories.RecipesRepository

class RecipesRepositoryImpl(
    private val api: API,
    private val database: Database
): RecipesRepository {
    override suspend fun getCategories(): Result<List<CategoryResponse>> {
        return try {
            val categoriesList = api.getCategories()

            Result.success(categoriesList.categories)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}