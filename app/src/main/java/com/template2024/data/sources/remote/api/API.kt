package com.template2024.data.sources.remote.api

import com.template2024.data.sources.remote.dto.response.CategoriesResponse
import com.template2024.data.sources.remote.dto.response.CategoryMealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("1/categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("1/filter.php")
    suspend fun getCategoryMealsList(
        @Query("c") categoryName: String
    ): CategoryMealsResponse
}