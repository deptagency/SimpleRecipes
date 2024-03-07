package com.template2024.ui.screens.home

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.template2024.R
import com.template2024.domain.models.Category
import com.template2024.ui.components.TopAppBar
import com.template2024.ui.theme.Template2024ApplicationTheme

@Composable
fun HomeScreen(
    homeUiState: HomeViewModel.HomeUiState,
    onCategorySelected: (String) -> Unit,
    imageButtonOneClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                appbarTitle = stringResource(id = R.string.home),
                imageButtonOneRes = R.drawable.ic_saved,
                imageButtonOneContentDescription = stringResource(R.string.favorite_content_description),
                imageButtonOneClicked = { imageButtonOneClicked() },
                imageButtonOneColor = Color.White
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
                when (homeUiState) {
                    is HomeViewModel.HomeUiState.Error -> {
                        Text(
                            text = homeUiState.errorMessage,
                            color = Color.Red
                        )
                    }

                    is HomeViewModel.HomeUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is HomeViewModel.HomeUiState.Idle -> {
                        val categories = homeUiState.categories

                        if (categories.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.padding(vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(categories) { category ->
                                    var isExpanded by remember { mutableStateOf(false) }
                                    ElevatedCard(
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { onCategorySelected(category.name) }
                                    ) {
                                        Row(modifier = Modifier.animateContentSize()) {
                                            Image(
                                                painter = rememberAsyncImagePainter(model = category.imageUrl),
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
                                                    text = category.name,
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                                Text(
                                                    text = category.description,
                                                    textAlign = TextAlign.Start,
                                                    color = LocalContentColor.current.copy(alpha = 0.75f),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    overflow = TextOverflow.Ellipsis,
                                                    maxLines = if (isExpanded) 10 else 4
                                                )
                                            }
                                            Icon(
                                                if (isExpanded)
                                                    Icons.Filled.KeyboardArrowUp
                                                else
                                                    Icons.Filled.KeyboardArrowDown,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .padding(16.dp)
                                                    .align(
                                                        if (isExpanded)
                                                            Alignment.Bottom
                                                        else
                                                            Alignment.CenterVertically
                                                    )
                                                    .clickable { isExpanded = !isExpanded }
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
    )
}

@Preview(apiLevel = 31, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val category = Category(
        id = "0",
        name = "Beef",
        description = "Healthy Beef Recipes",
        imageUrl = "https://example.com/image.jpg"
    )
    Template2024ApplicationTheme {
        HomeScreen(homeUiState = HomeViewModel.HomeUiState.Idle(
            listOf(
                category, category, category
            )
        ),
            onCategorySelected = {},
            imageButtonOneClicked = {}
        )
    }
}