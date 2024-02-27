package com.template2024.data.sources.remote.dto.response

import com.google.gson.annotations.SerializedName

data class CategoryMealsResponse(
    @SerializedName("meals")
    val mealsList: List<MealResponse>
)
