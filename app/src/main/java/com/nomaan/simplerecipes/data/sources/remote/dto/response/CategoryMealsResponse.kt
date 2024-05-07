package com.nomaan.simplerecipes.data.sources.remote.dto.response

import com.google.gson.annotations.SerializedName

data class CategoryMealsResponse(
    @SerializedName("meals")
    val mealsList: List<MealResponse>
)
