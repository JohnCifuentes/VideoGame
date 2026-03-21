package com.example.nuestrovideojuego.core.navigation

import kotlinx.serialization.Serializable

sealed class MainRoutes {

    @Serializable
    data object Home : MainRoutes()

    @Serializable
    data class VideoGame(val userName: String) : MainRoutes()

    @Serializable
    data class Result(
        val userName: String,
        val totalRounds: Int,
        val correctAnswers: Int,
        val incorrectAnswers: Int
    ) : MainRoutes()

}