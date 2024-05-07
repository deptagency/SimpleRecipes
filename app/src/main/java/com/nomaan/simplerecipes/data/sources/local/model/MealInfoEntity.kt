package com.nomaan.simplerecipes.data.sources.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nomaan.simplerecipes.domain.models.MealInfo

@Entity(tableName = "mealInfo")
data class MealInfoEntity(
    @PrimaryKey val mealId: String,
    val mealName: String,
    val region: String,
    val mealThumbNail: String,
    val mealInstructions: String,
    val mealIngredientList: List<Pair<String, String>>,
    val savedToDB: Boolean
)

fun MealInfoEntity.toMealInfo(): MealInfo {
    return MealInfo(
        mealId = mealId,
        mealName = mealName,
        region = region,
        mealThumbNail = mealThumbNail,
        mealInstructions = mealInstructions,
        mealIngredientList = mealIngredientList,
        savedToDB = true
    )
}

fun MealInfo.toMealInfoEntity(): MealInfoEntity {
    return MealInfoEntity(
        mealId = mealId,
        mealName = mealName,
        region = region,
        mealThumbNail = mealThumbNail,
        mealInstructions = mealInstructions,
        mealIngredientList = mealIngredientList,
        savedToDB = true
    )
}
