package com.nomaan.simplerecipes.ui

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
import com.nomaan.simplerecipes.ui.navigation.Graph
import com.nomaan.simplerecipes.ui.navigation.MainRoute
import com.nomaan.simplerecipes.ui.navigation.NavAnimation.fadeIn
import com.nomaan.simplerecipes.ui.navigation.NavAnimation.slideInFromRight
import com.nomaan.simplerecipes.ui.navigation.NavAnimation.slideInLikeBottomSheet
import com.nomaan.simplerecipes.ui.navigation.NavAnimation.slideOutLikeBottomSheet
import com.nomaan.simplerecipes.ui.navigation.NavAnimation.slideOutToRight
import com.nomaan.simplerecipes.ui.navigation.ParameterNames.CATEGORY_NAME
import com.nomaan.simplerecipes.ui.navigation.ParameterNames.MEAL_ID
import com.nomaan.simplerecipes.ui.screens.home.HomeScreen
import com.nomaan.simplerecipes.ui.screens.home.HomeViewModel
import com.nomaan.simplerecipes.ui.screens.mealdetails.MealDetailsScreen
import com.nomaan.simplerecipes.ui.screens.mealdetails.MealDetailsViewModel
import com.nomaan.simplerecipes.ui.screens.meals.MealsScreen
import com.nomaan.simplerecipes.ui.screens.meals.MealsViewModel
import com.nomaan.simplerecipes.ui.screens.savedmealslist.SavedMealsListScreen
import com.nomaan.simplerecipes.ui.screens.savedmealslist.SavedMealsListViewModel
import com.nomaan.simplerecipes.ui.theme.SimpleRecipesAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleRecipesAppTheme {
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
                                },
                                imageButtonOneClicked = {
                                    mainNavController.navigate(
                                        MainRoute.SavedMeals.name
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
                            val showMealsInformationOverlay by mealsViewModel.showInformationOverlay.collectAsStateWithLifecycle()

                            MealsScreen(
                                appBarTitle = category,
                                mealsListUiState = mealsListUiState,
                                showMealsInformationOverlay = showMealsInformationOverlay,
                                onMealClicked = { mealId ->
                                    mainNavController.navigate(
                                        MainRoute.MealDetails.name
                                            .replace("{$MEAL_ID}", mealId)
                                    )
                                },
                                onMealsInformationOverlayDismissed = { mealsViewModel.dismissMealsInformationOverlayClicked() }
                            ) { mainNavController.popBackStack() }
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
                        composable(
                            route = MainRoute.SavedMeals.name,
                            enterTransition = { slideInLikeBottomSheet },
                            popEnterTransition = { fadeIn },
                            popExitTransition = { slideOutLikeBottomSheet }
                        ) {
                            val savedMealsListViewModel = koinViewModel<SavedMealsListViewModel>()
                            val savedMealsListUiState by savedMealsListViewModel.savedMealsListUiState.collectAsStateWithLifecycle()

                            SavedMealsListScreen(
                                savedMealsListUiState = savedMealsListUiState,
                                onMealClicked = { mealId ->
                                    mainNavController.navigate(
                                        MainRoute.MealDetails.name
                                            .replace("{$MEAL_ID}", mealId)
                                    )
                                },
                                onBackPressed = { mainNavController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

