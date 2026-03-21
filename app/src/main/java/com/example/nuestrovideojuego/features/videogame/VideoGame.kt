package com.example.nuestrovideojuego.features.videogame

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun VideoGame(
    userName: String,
    onNavigateToResult: (String, Int, Int, Int) -> Unit,
    viewModel: VideoGameViewModel = viewModel()
) {
    LaunchedEffect(viewModel.isGameOver) {
        if (viewModel.isGameOver && viewModel.timeLeft <= 0) {
            onNavigateToResult(
                userName,
                viewModel.totalRounds,
                viewModel.score,
                viewModel.incorrectAnswers
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Temporizador en la parte superior
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tiempo: ${viewModel.timeLeft}s",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = if (viewModel.timeLeft < 10) Color.Red else Color.Black
        )

        // Contenido central: Carta 1, Mensaje, Carta 2 (centrado verticalmente)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            viewModel.topCard?.let { card ->
                MathCardUI(
                    card = card,
                    isRevealed = viewModel.isRevealed,
                    onClick = { viewModel.onCardSelected(true) }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = viewModel.message,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (viewModel.message.contains("Error")) Color.Red else Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))

            viewModel.bottomCard?.let { card ->
                MathCardUI(
                    card = card,
                    isRevealed = viewModel.isRevealed,
                    onClick = { viewModel.onCardSelected(false) }
                )
            }
        }

        // Botones en la parte inferior
        if (viewModel.isRevealed && !viewModel.isGameOver) {
            Button(
                onClick = { viewModel.generateNewRound() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A1B9A)
                )
            ) {
                Text(
                    text = "Siguiente Ronda",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                onNavigateToResult(
                    userName,
                    viewModel.totalRounds,
                    viewModel.score,
                    viewModel.incorrectAnswers
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray
            )
        ) {
            Text(
                text = "Salir",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MathCardUI(
    card: MathCard,
    isRevealed: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable(enabled = !isRevealed, onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = card.expression,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                if (isRevealed) {
                    Text(
                        text = "= ${card.value}",
                        fontSize = 28.sp,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
