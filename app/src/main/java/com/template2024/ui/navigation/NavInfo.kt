package com.template2024.ui.navigation

import com.template2024.ui.navigation.ParameterNames.RECIPE_ID

object Graph {
    const val MAIN = "main_graph"
}

object ParameterNames {
    const val RECIPE_ID = "appBarTitle"
}

sealed class MainRoute(val name: String) {
    data object Home: MainRoute("Home")
    data object RecipeDetail: MainRoute("RecipeDetail/{$RECIPE_ID}")
}