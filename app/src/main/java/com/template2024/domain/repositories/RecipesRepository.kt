package com.template2024.domain.repositories

import com.template2024.data.sources.remote.dto.response.CategoryResponse

interface RecipesRepository {
    suspend fun getCategories(): Result<List<CategoryResponse>>
}