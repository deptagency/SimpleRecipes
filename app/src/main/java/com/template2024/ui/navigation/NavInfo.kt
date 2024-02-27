package com.template2024.ui.navigation

import com.template2024.ui.navigation.ParameterNames.CATEGORY_NAME

object Graph {
    const val MAIN = "main_graph"
}

object ParameterNames {
    const val CATEGORY_NAME = "categoryName"
}

sealed class MainRoute(val name: String) {
    data object Home: MainRoute("Home")
    data object MealsList: MainRoute("MealsList/{$CATEGORY_NAME}")
}