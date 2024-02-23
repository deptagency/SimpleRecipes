package com.template2024.data.sources.remote.dto.response

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories")
    val categories: List<CategoryResponse>
)