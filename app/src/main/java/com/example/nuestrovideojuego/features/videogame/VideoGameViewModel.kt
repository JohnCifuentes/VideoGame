package com.example.nuestrovideojuego.features.videogame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

data class MathCard(
    val expression: String,
    val value: Int
)

class VideoGameViewModel : ViewModel() {

    var topCard by mutableStateOf<MathCard?>(null)
        private set

    var bottomCard by mutableStateOf<MathCard?>(null)
        private set

    var isRevealed by mutableStateOf(false)
        private set

    var score by mutableStateOf(0)
        private set

    var incorrectAnswers by mutableStateOf(0)
        private set

    val totalRounds: Int
        get() = score + incorrectAnswers

    var message by mutableStateOf("¿Cuál es mayor?")
        private set

    var isGameOver by mutableStateOf(false)
        private set

    var timeLeft by mutableStateOf(60)
        private set

    private var timerJob: Job? = null

    init {
        generateNewRound()
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            isGameOver = true
        }
    }

    fun generateNewRound() {
        if (isGameOver && timeLeft <= 0) return // Don't generate if time is up
        
        topCard = generateExpression()
        bottomCard = generateExpression()
        while (topCard?.value == bottomCard?.value) {
            bottomCard = generateExpression()
        }
        isRevealed = false
        isGameOver = false
        message = "¿Cuál es mayor?"
    }

    private fun generateExpression(): MathCard {
        val operators = listOf("+", "-", "*", "/")
        val operator = operators.random()
        var a = 0
        var b = 0
        var result = 0

        do {
            when (operator) {
                "+" -> {
                    a = Random.nextInt(1, 20)
                    b = Random.nextInt(1, 21 - a)
                    result = a + b
                }
                "-" -> {
                    a = Random.nextInt(2, 21)
                    b = Random.nextInt(1, a)
                    result = a - b
                }
                "*" -> {
                    val factors = mutableListOf<Int>()
                    val r = Random.nextInt(1, 21)
                    for (i in 1..r) {
                        if (r % i == 0) factors.add(i)
                    }
                    a = factors.random()
                    b = r / a
                    result = r
                }
                "/" -> {
                    b = Random.nextInt(1, 10)
                    result = Random.nextInt(1, 21)
                    a = result * b
                }
            }
        } while (result !in 1..20 || (operator == "*" && a == 1 && b == result && Random.nextBoolean()))

        val expr = when (operator) {
            "*" -> "$a × $b"
            "/" -> "$a ÷ $b"
            else -> "$a $operator $b"
        }

        return MathCard(expr, result)
    }

    fun onCardSelected(isTopSelected: Boolean) {
        if (isRevealed || isGameOver) return

        isRevealed = true
        val topVal = topCard?.value ?: 0
        val bottomVal = bottomCard?.value ?: 0

        val won = if (isTopSelected) topVal > bottomVal else bottomVal > topVal

        if (won) {
            score++
            message = "¡Correcto!"
        } else {
            incorrectAnswers++
            message = "¡Error! ${topVal} vs ${bottomVal}"
            // isGameOver = true // No longer game over on single mistake, just continue or wait for timer
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

}
