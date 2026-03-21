package com.example.nuestrovideojuego.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.nuestrovideojuego.features.home.HomeScreen
import com.example.nuestrovideojuego.features.result.ResultScreen
import com.example.nuestrovideojuego.features.videogame.VideoGame

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = MainRoutes.Home
        ) {

            composable<MainRoutes.Home> {
                HomeScreen(
                    onNavigateToVideoGame = { name ->
                        navController.navigate(MainRoutes.VideoGame(userName = name))
                    }
                )
            }

            composable<MainRoutes.VideoGame> {
                val args = it.toRoute<MainRoutes.VideoGame>()
                VideoGame(
                    userName = args.userName,
                    onNavigateToResult = { name, total, correct, incorrect ->
                        navController.navigate(
                            MainRoutes.Result(
                                userName = name,
                                totalRounds = total,
                                correctAnswers = correct,
                                incorrectAnswers = incorrect
                            )
                        )
                    }
                )
            }

            composable<MainRoutes.Result> {
                val args = it.toRoute<MainRoutes.Result>()
                ResultScreen(
                    userName = args.userName,
                    totalRounds = args.totalRounds,
                    correctAnswers = args.correctAnswers,
                    incorrectAnswers = args.incorrectAnswers,
                    onPlayAgain = {
                        navController.navigate(MainRoutes.VideoGame(userName = args.userName)) {
                            popUpTo(MainRoutes.Home)
                        }
                    },
                    onGoHome = {
                        navController.navigate(MainRoutes.Home) {
                            popUpTo(MainRoutes.Home) { inclusive = true }
                        }
                    }
                )
            }

        }
    }

}
