package com.nomaan.simplerecipes.ui.screens.mealdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.nomaan.simplerecipes.R
import com.nomaan.simplerecipes.domain.models.MealInfo
import com.nomaan.simplerecipes.ui.components.TopAppBar
import com.nomaan.simplerecipes.ui.theme.SimpleRecipesAppTheme

@Composable
fun MealDetailsScreen(
    mealDetailsUiState: MealDetailsViewModel.MealDetailsUiState,
    onBackPressed: () -> Unit,
    imageButtonOneClicked: () -> Unit
) {
    val context = LocalContext.current
    var appBarTitle by remember { mutableStateOf(context.getString(R.string.app_name)) }
    var appBarFavoriteColor by remember { mutableStateOf(Color.White) }

    Scaffold(
        topBar = {
            TopAppBar(
                appbarTitle = appBarTitle,
                onBackPressed = { onBackPressed() },
                imageButtonOneRes = R.drawable.ic_favorite,
                imageButtonOneContentDescription = stringResource(R.string.favorite_content_description),
                imageButtonOneClicked = { imageButtonOneClicked() },
                imageButtonOneColor = appBarFavoriteColor
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(16.dp),
            ) {
                when (mealDetailsUiState) {
                    is MealDetailsViewModel.MealDetailsUiState.Error -> {
                        Text(
                            text = mealDetailsUiState.errorMessage,
                            color = Color.Red
                        )
                    }

                    is MealDetailsViewModel.MealDetailsUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is MealDetailsViewModel.MealDetailsUiState.Idle -> {
                        val mealInfo = mealDetailsUiState.mealInfo

                        if (mealInfo != null) {
                            appBarTitle = mealInfo.mealName
                            appBarFavoriteColor = if (mealInfo.savedToDB) Color.Red else Color.White

                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(mealInfo.mealThumbNail)
                                    .memoryCachePolicy(CachePolicy.ENABLED)
                                    .diskCachePolicy(CachePolicy.ENABLED)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = stringResource(R.string.recipe_image_content_description),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )

                            Text(
                                text = "${stringResource(id = R.string.cuisine_header)}: ${mealInfo.region}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = stringResource(id = R.string.ingredients_header),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                            )

                            for (ingredient in mealInfo.mealIngredientList) {
                                Text(text = "${ingredient.first} (${ingredient.second})")
                            }

                            Text(
                                text = stringResource(id = R.string.instructions_header),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                            )

                            Text(
                                text = mealInfo.mealInstructions,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(apiLevel = 31, showSystemUi = true)
@Composable
fun MealDetailsScreenPreview() {
    val mealInfo = MealInfo(
        mealId = "123",
        mealName = "Beef Broth",
        region = "Japanese",
        mealThumbNail = "https://example.com/image.jpg",
        mealInstructions = "Cook it.\r\nServe it.",
        mealIngredientList = listOf(
            Pair("1 Heart", "Love"),
            Pair("2 Cans", "Coke")
        ),
        savedToDB = false
    )

    SimpleRecipesAppTheme {
        MealDetailsScreen(
            mealDetailsUiState = MealDetailsViewModel.MealDetailsUiState.Idle(mealInfo),
            onBackPressed = {},
            imageButtonOneClicked = {}
        )
    }
}