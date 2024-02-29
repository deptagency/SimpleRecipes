package com.template2024.domain.models

data class MealInfo(
    val mealId: String,
    val mealName: String,
    val region: String,
    val mealThumbNail: String,
    val mealInstructions: String,
    val mealIngredientList: List<Pair<String?, String?>>,
)
