package com.nomaan.simplerecipes.ui.screens.savedmealslist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nomaan.simplerecipes.R
import com.nomaan.simplerecipes.domain.models.MealInfo
import com.nomaan.simplerecipes.ui.components.TopAppBar
import com.nomaan.simplerecipes.ui.theme.SimpleRecipesAppTheme

@Composable
fun SavedMealsListScreen(
    savedMealsListUiState: SavedMealsListViewModel.SavedMealsListUiState,
    onMealClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                appbarTitle = stringResource(id = R.string.saved_recipes),
                onBackPressed = { onBackPressed() }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(padding)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (savedMealsListUiState) {
                    is SavedMealsListViewModel.SavedMealsListUiState.Error -> {
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = savedMealsListUiState.errorMessage,
                                color = Color.Red,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                    }

                    is SavedMealsListViewModel.SavedMealsListUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is SavedMealsListViewModel.SavedMealsListUiState.Idle -> {
                        val meals = savedMealsListUiState.meals

                        if (meals.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.padding(vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(meals) { meal ->
                                    ElevatedCard(
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { onMealClicked(meal.mealId) }
                                    ) {
                                        Row(modifier = Modifier.animateContentSize()) {
                                            Image(
                                                painter = rememberAsyncImagePainter(model = meal.mealThumbNail),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(80.dp)
                                                    .padding(4.dp)
                                                    .align(Alignment.CenterVertically)
                                            )
                                            Column(
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .fillMaxWidth(0.8f)
                                                    .padding(16.dp)
                                            ) {
                                                Text(
                                                    text = meal.mealName,
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                                Text(
                                                    text = meal.mealInstructions,
                                                    textAlign = TextAlign.Start,
                                                    color = LocalContentColor.current.copy(alpha = 0.75f),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    overflow = TextOverflow.Ellipsis,
                                                    maxLines = 4
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(apiLevel = 31, showSystemUi = true)
@Composable
fun SavedMealsListScreenPreview() {
    val meal = MealInfo(
        mealId = "123",
        mealName = "Beef Broth",
        region = "Japanese",
        mealThumbNail = "https://example.com/image.jpg",
        mealInstructions = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        mealIngredientList = emptyList(),
        savedToDB = true
    )

    SimpleRecipesAppTheme {
        SavedMealsListScreen(
            savedMealsListUiState = SavedMealsListViewModel.SavedMealsListUiState.Idle(
                listOf(
                    meal, meal, meal
                )
            ),
//            savedMealsListUiState = SavedMealsListViewModel.SavedMealsListUiState.Error("No saved meals."),
            onMealClicked = {},
            onBackPressed = {}
        )
    }
}