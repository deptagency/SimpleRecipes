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
import com.template2024.ui.screens.HomeScreen
import com.template2024.ui.screens.HomeViewModel
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
                        composable(route = MainRoute.Home.name) {
                            val homeViewModel = koinViewModel<HomeViewModel>()
                            val homeUiState by homeViewModel.homeUiState.collectAsStateWithLifecycle()

                            HomeScreen(
                                homeUiState = homeUiState,
                                onBackPressed = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

