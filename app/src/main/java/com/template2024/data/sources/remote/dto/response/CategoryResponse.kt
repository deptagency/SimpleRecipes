package com.template2024.data.sources.remote.dto.response

import com.google.gson.annotations.SerializedName

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