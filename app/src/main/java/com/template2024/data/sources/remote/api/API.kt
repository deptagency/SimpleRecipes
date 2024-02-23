package com.template2024.data.sources.remote.api

import com.template2024.data.sources.remote.dto.response.CategoriesResponse
import retrofit2.http.GET

interface API {
    @GET("1/categories.php")
    suspend fun getCategories(): CategoriesResponse
}