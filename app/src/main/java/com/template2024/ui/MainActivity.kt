package com.template2024.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.template2024.ui.navigation.Graph
import com.template2024.ui.navigation.MainRoute
import com.template2024.ui.navigation.NavAnimation.fadeIn
import com.template2024.ui.navigation.NavAnimation.slideInFromRight
import com.template2024.ui.navigation.NavAnimation.slideInLikeBottomSheet
import com.template2024.ui.navigation.NavAnimation.slideOutLikeBottomSheet
import com.template2024.ui.navigation.NavAnimation.slideOutToRight
import com.template2024.ui.navigation.ParameterNames.CATEGORY_NAME
import com.template2024.ui.navigation.ParameterNames.MEAL_ID
import com.template2024.ui.screens.home.HomeScreen
import com.template2024.ui.screens.home.HomeViewModel
import com.template2024.ui.screens.mealdetails.MealDetailsScreen
import com.template2024.ui.screens.mealdetails.MealDetailsViewModel
import com.template2024.ui.screens.meals.MealsListScreen
import com.template2024.ui.screens.meals.MealsViewModel
import com.template2024.ui.theme.Template2024ApplicationTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Template2024ApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainNavController = rememberNavController()

                    NavHost(
                        navController = mainNavController,
                        route = Graph.MAIN,
                        startDestination = MainRoute.Home.name
                    ) {
                        composable(
                            route = MainRoute.Home.name,
                            popEnterTransition = { fadeIn },
                            popExitTransition = { slideOutLikeBottomSheet }
                        ) {
                            val homeViewModel = koinViewModel<HomeViewModel>()
                            val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()

                            HomeScreen(
                                homeUiState = homeUiState,
                                onCategorySelected = { category ->
                                    mainNavController.navigate(
                                        MainRoute.MealsList.name
                                            .replace("{$CATEGORY_NAME}", category)
                                    )
                                }
                            )
                        }
                        composable(
                            route = MainRoute.MealsList.name,
                            enterTransition = { slideInFromRight() },
                            popEnterTransition = { fadeIn },
                            popExitTransition = { slideOutToRight() }
                        ) {
                            val category =
                                it.arguments?.getString(CATEGORY_NAME, "")
                                    ?: ""

                            val mealsViewModel = koinViewModel<MealsViewModel>()
                            val mealsListUiState by mealsViewModel.mealsListUiState.collectAsStateWithLifecycle()

                            MealsListScreen(
                                appBarTitle = category,
                                mealsListUiState = mealsListUiState,
                                onMealClicked = { mealId ->
                                    mainNavController.navigate(
                                        MainRoute.MealDetails.name
                                            .replace("{$MEAL_ID}", mealId)
                                    )
                                },
                                onBackPressed = { mainNavController.popBackStack() }
                            )
                        }
                        composable(
                            route = MainRoute.MealDetails.name,
                            enterTransition = { slideInLikeBottomSheet },
                            popExitTransition = { slideOutLikeBottomSheet }
                        ) {
                            val mealDetailsViewModel = koinViewModel<MealDetailsViewModel>()
                            val mealsDetailsUiState by mealDetailsViewModel.mealDetailsUiState.collectAsStateWithLifecycle()

                            MealDetailsScreen(
                                mealDetailsUiState = mealsDetailsUiState,
                                onBackPressed = { mainNavController.popBackStack() },
                                imageButtonOneClicked = { mealDetailsViewModel.saveOrDeleteMealDetails() }
                            )
                        }
                    }
                }
            }
        }
    }
}

