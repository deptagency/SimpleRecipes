package com.template2024.ui.screens.meals

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.template2024.R
import com.template2024.domain.models.Meal
import com.template2024.ui.components.TopAppBar
import com.template2024.ui.theme.Template2024ApplicationTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealsScreen(
    appBarTitle: String,
    mealsListUiState: MealsViewModel.MealsListUiState,
    showMealsInformationOverlay: Boolean,
    onMealClicked: (String) -> Unit,
    onMealsInformationOverlayDismissed: () -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                appbarTitle = appBarTitle,
                onBackPressed = { onBackPressed() }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(padding)
            ) {
                when (mealsListUiState) {
                    is MealsViewModel.MealsListUiState.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = mealsListUiState.errorMessage,
                                color = Color.Red
                            )
                        }
                    }

                    is MealsViewModel.MealsListUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is MealsViewModel.MealsListUiState.Idle -> {
                        val meals = mealsListUiState.meals

                        if (meals.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            ) {
                                val pagerState = rememberPagerState {
                                    meals.size
                                }

                                HorizontalPager(
                                    state = pagerState
                                ) { page ->
                                    CarouselItem(
                                        meals[page],
                                        onItemClicked = onMealClicked
                                    )
                                }
                            }
                            if (showMealsInformationOverlay) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)),
                                ) {
                                    Column(
                                        Modifier
                                            .weight(1f)
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = R.drawable.ic_tap),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(48.dp)
                                                .padding(4.dp),
                                            colorFilter = ColorFilter.tint(color = Color.Black)
                                        )
                                        Text(text = "Tap to view details.")
                                    }
                                    Column(
                                        Modifier
                                            .weight(1f)
                                            .fillMaxSize()
                                            .padding(end = 16.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(model = R.drawable.ic_swipe_left),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(48.dp)
                                                .padding(4.dp),
                                            colorFilter = ColorFilter.tint(color = Color.Black)
                                        )
                                        Text(text = "Swipe to view next recipe.")
                                    }
                                    Column(
                                        Modifier
                                            .weight(1f)
                                            .fillMaxSize()
                                            .padding(bottom = 16.dp),
                                        verticalArrangement = Arrangement.Bottom,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Button(
                                            onClick = { onMealsInformationOverlayDismissed() }
                                        ) {
                                            Text(text = "Dismiss")
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
        MealsScreen(
            appBarTitle = "Beef",
            mealsListUiState = MealsViewModel.MealsListUiState.Idle(
                listOf(
                    meal, meal, meal
                )
            ),
            showMealsInformationOverlay = true,
            onMealClicked = {},
            onMealsInformationOverlayDismissed = {}
        ) {}
    }
}