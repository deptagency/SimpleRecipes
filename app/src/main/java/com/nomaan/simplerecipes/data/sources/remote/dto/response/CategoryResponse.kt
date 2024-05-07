package com.nomaan.simplerecipes.data.sources.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.nomaan.simplerecipes.domain.models.Category

data class CategoryResponse(
    @SerializedName("idCategory")
    val id: String,

    @SerializedName("strCategory")
    val name: String,

    @SerializedName("strCategoryDescription")
    val description: String,

    @SerializedName("strCategoryThumb")
    val imageUrl: String
)

fun CategoryResponse.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl
    )
}