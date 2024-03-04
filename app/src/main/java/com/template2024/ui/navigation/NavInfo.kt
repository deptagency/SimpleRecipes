package com.template2024.ui.navigation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import com.template2024.ui.navigation.ParameterNames.CATEGORY_NAME
import com.template2024.ui.navigation.ParameterNames.MEAL_ID

object Graph {
    const val MAIN = "main_graph"
}

object ParameterNames {
    const val CATEGORY_NAME = "categoryName"
    const val MEAL_ID = "mealId"
}

sealed class MainRoute(val name: String) {
    data object Home: MainRoute("Home")
    data object SavedMeals: MainRoute("SavedMeals")
    data object MealsList: MainRoute("MealsList/{$CATEGORY_NAME}")
    data object MealDetails: MainRoute("MealsDetails/{$MEAL_ID}")
}

object NavAnimation {
    val slideInLikeBottomSheet = slideInVertically(
        initialOffsetY = {
            it * 2
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val slideOutLikeBottomSheet = slideOutVertically(
        targetOffsetY = {
            it * 2
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing)
    )

    fun slideInFromRight(duration: Int = 300) = slideInHorizontally(
        initialOffsetX = {
            it * 2
        },
        animationSpec = tween(durationMillis = duration, easing = FastOutSlowInEasing)
    )

    fun slideOutToRight(duration: Int = 300) = slideOutHorizontally(
        targetOffsetX = {
            it * 2
        },
        animationSpec = tween(durationMillis = duration, easing = FastOutLinearInEasing)
    )

    val fadeIn = fadeIn(
        animationSpec = tween(durationMillis = 10)
    )
}