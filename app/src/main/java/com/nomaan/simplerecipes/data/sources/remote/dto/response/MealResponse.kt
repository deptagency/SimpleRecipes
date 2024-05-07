package com.nomaan.simplerecipes.data.sources.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.nomaan.simplerecipes.domain.models.Meal

data class MealResponse(
    @SerializedName("strMeal")
    val mealName: String,

    @SerializedName("strMealThumb")
    val mealThumbNail: String,

    @SerializedName("idMeal")
    val mealId: String
)

fun MealResponse.toMeal() =
    Meal(
        mealName = this.mealName,
        mealThumbNail = this.mealThumbNail,
        mealId = this.mealId
    )