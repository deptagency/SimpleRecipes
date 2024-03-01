package com.template2024.ui.screens.meals

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.template2024.R
import com.template2024.domain.models.Meal
import com.template2024.ui.components.TopAppBar
import com.template2024.ui.theme.Template2024ApplicationTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealsListScreen(
    appBarTitle: String,
    mealsListUiState: MealsViewModel.MealsListUiState,
    onMealClicked: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                appbarTitle = appBarTitle,
                onBackPressed =  { onBackPressed() }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(padding)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (mealsListUiState) {
                    is MealsViewModel.MealsListUiState.Error -> {
                        Text(
                            text = mealsListUiState.errorMessage,
                            color = Color.Red
                        )
                    }

                    is MealsViewModel.MealsListUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is MealsViewModel.MealsListUiState.Idle -> {
                        val meals = mealsListUiState.meals

                        if (meals.isNotEmpty()) {
                            Column(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                val pagerState = rememberPagerState {
                                    meals.size
                                }

                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier.weight(1f)
                                ) { page ->
                                    CarouselItem(
                                        meals[page],
                                        onItemClicked = onMealClicked
                                    )
                                }

                                Row(
                                    Modifier
                                        .height(50.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    repeat(meals.size) { iteration ->
                                        val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                                        Box(
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .background(color, CircleShape)
                                                .size(8.dp)
                                        )
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

@Composable
fun CarouselItem(meal: Meal, onItemClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(meal.mealThumbNail)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.recipe_image_content_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .weight(0.9f)
                .clip(RoundedCornerShape(16.dp))
                .clickable { onItemClicked(meal.mealId) }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(0.1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = meal.mealName,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Preview(apiLevel = 31, showSystemUi = true)
@Composable
fun MealsScreenPreview() {
    val meal = Meal(
        mealName = "Beef Broth",
        mealThumbNail = "https://example.com/image.jpg",
        mealId = "123"
    )

    Template2024ApplicationTheme {
        MealsListScreen(
            appBarTitle = "Beef",
            mealsListUiState = MealsViewModel.MealsListUiState.Idle(
                listOf(
                    meal, meal, meal
                )
            ),
            onMealClicked = {},
            onBackPressed = {}
        )
    }
}