package com.template2024.ui.screens.meals

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.template2024.domain.models.Meal
import com.template2024.ui.components.TopAppBar
import com.template2024.ui.theme.Template2024ApplicationTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealsListScreen(
    appBarTitle: String,
    mealsListUiState: MealsViewModel.MealsListUiState,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                appbarTitle = appBarTitle
            ) { onBackPressed() }
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
                                    CarouselItem(meals[page])
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
fun CarouselItem(meal: Meal) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = meal.mealThumbNail),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(0.9f)
                .clip(RoundedCornerShape(16.dp))
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
fun DefaultPreview() {
    val meal = Meal(
        mealName = "Beef Broth",
        mealThumbNail = "https://example.com/image.jpg",
        mealId = "123"
    )

    Template2024ApplicationTheme {
        MealsListScreen(
            appBarTitle = "Home",
            mealsListUiState = MealsViewModel.MealsListUiState.Idle(
                listOf(
                    meal, meal, meal
                )
            ),
            onBackPressed = {}
        )
    }
}

@Preview(apiLevel = 31)
@Composable
fun CarouselItemPreview() {
    Template2024ApplicationTheme {
        val meal = Meal(
            mealName = "Beef Broth",
            mealThumbNail = "https://example.com/image.jpg",
            mealId = "123"
        )
        Column(
            Modifier
                .wrapContentSize()
                .background(Color.White)
        ) {
            CarouselItem(meal = meal)
        }
    }
}