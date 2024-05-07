package com.nomaan.simplerecipes.data.sources.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.nomaan.simplerecipes.domain.models.MealInfo

data class MealDetailsResponse(
    @SerializedName("meals")
    val meals: List<MealInfoResponse>,
)

data class MealInfoResponse(
    @SerializedName("idMeal")
    val mealId: String,

    @SerializedName("strMeal")
    val mealName: String,

    @SerializedName("strArea")
    val region: String,

    @SerializedName("strMealThumb")
    val mealThumbNail: String,

    @SerializedName("strInstructions")
    val mealInstructions: String,

    @SerializedName("strIngredient1")
    val strIngredient1: String?,

    @SerializedName("strIngredient2")
    val strIngredient2: String?,

    @SerializedName("strIngredient3")
    val strIngredient3: String?,

    @SerializedName("strIngredient4")
    val strIngredient4: String?,

    @SerializedName("strIngredient5")
    val strIngredient5: String?,

    @SerializedName("strIngredient6")
    val strIngredient6: String?,

    @SerializedName("strIngredient7")
    val strIngredient7: String?,

    @SerializedName("strIngredient8")
    val strIngredient8: String?,

    @SerializedName("strIngredient9")
    val strIngredient9: String?,

    @SerializedName("strIngredient10")
    val strIngredient10: String?,

    @SerializedName("strIngredient11")
    val strIngredient11: String?,

    @SerializedName("strIngredient12")
    val strIngredient12: String?,

    @SerializedName("strIngredient13")
    val strIngredient13: String?,

    @SerializedName("strIngredient14")
    val strIngredient14: String?,

    @SerializedName("strIngredient15")
    val strIngredient15: String?,

    @SerializedName("strIngredient16")
    val strIngredient16: String?,

    @SerializedName("strIngredient17")
    val strIngredient17: String?,

    @SerializedName("strIngredient18")
    val strIngredient18: String?,

    @SerializedName("strIngredient19")
    val strIngredient19: String?,

    @SerializedName("strIngredient20")
    val strIngredient20: String?,

    @SerializedName("strMeasure1")
    val strMeasure1: String?,

    @SerializedName("strMeasure2")
    val strMeasure2: String?,

    @SerializedName("strMeasure3")
    val strMeasure3: String?,

    @SerializedName("strMeasure4")
    val strMeasure4: String?,

    @SerializedName("strMeasure5")
    val strMeasure5: String?,

    @SerializedName("strMeasure6")
    val strMeasure6: String?,

    @SerializedName("strMeasure7")
    val strMeasure7: String?,

    @SerializedName("strMeasure8")
    val strMeasure8: String?,

    @SerializedName("strMeasure9")
    val strMeasure9: String?,

    @SerializedName("strMeasure10")
    val strMeasure10: String?,

    @SerializedName("strMeasure11")
    val strMeasure11: String?,

    @SerializedName("strMeasure12")
    val strMeasure12: String?,

    @SerializedName("strMeasure13")
    val strMeasure13: String?,

    @SerializedName("strMeasure14")
    val strMeasure14: String?,

    @SerializedName("strMeasure15")
    val strMeasure15: String?,

    @SerializedName("strMeasure16")
    val strMeasure16: String?,

    @SerializedName("strMeasure17")
    val strMeasure17: String?,

    @SerializedName("strMeasure18")
    val strMeasure18: String?,

    @SerializedName("strMeasure19")
    val strMeasure19: String?,

    @SerializedName("strMeasure20")
    val strMeasure20: String?
)

fun MealInfoResponse.toMealInfo(): MealInfo {
    val ingredientsList = listOf(
        Pair(strIngredient1, strMeasure1?.trim()),
        Pair(strIngredient2, strMeasure2?.trim()),
        Pair(strIngredient3, strMeasure3?.trim()),
        Pair(strIngredient4, strMeasure4?.trim()),
        Pair(strIngredient5, strMeasure5?.trim()),
        Pair(strIngredient6, strMeasure6?.trim()),
        Pair(strIngredient7, strMeasure7?.trim()),
        Pair(strIngredient8, strMeasure8?.trim()),
        Pair(strIngredient9, strMeasure9?.trim()),
        Pair(strIngredient10, strMeasure10?.trim()),
        Pair(strIngredient11, strMeasure11?.trim()),
        Pair(strIngredient12, strMeasure12?.trim()),
        Pair(strIngredient13, strMeasure13?.trim()),
        Pair(strIngredient14, strMeasure14?.trim()),
        Pair(strIngredient15, strMeasure15?.trim()),
        Pair(strIngredient16, strMeasure16?.trim()),
        Pair(strIngredient17, strMeasure17?.trim()),
        Pair(strIngredient18, strMeasure18?.trim()),
        Pair(strIngredient19, strMeasure19?.trim()),
        Pair(strIngredient20, strMeasure20?.trim())
    )

    val filteredIngredientsList = ingredientsList
        .filter { !it.first.isNullOrBlank() && !it.second.isNullOrBlank() } as List<Pair<String, String>>

    return MealInfo(
        mealId = mealId,
        mealName = mealName,
        region = region,
        mealThumbNail = mealThumbNail,
        mealInstructions = mealInstructions,
        mealIngredientList = filteredIngredientsList,
        savedToDB = false
    )
}
