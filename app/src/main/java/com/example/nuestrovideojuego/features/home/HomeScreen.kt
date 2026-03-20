package com.example.nuestrovideojuego.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuestrovideojuego.R

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreen(
    viewModel: HomeScreenViewModel = viewModel()
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // fondo claro como mockup
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier.size(250.dp),
            painter = painterResource(id = R.drawable.videogame),
            contentDescription = "Logo"
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Domina las Matemáticas",
            fontSize = 26.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Descubre el poder de los numeros en un desafío táctico de cartas ¿Podrás encontrar la operación de mayor valor?",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = viewModel.userName.value,
            onValueChange = { viewModel.userName.onChange(it) },
            label = { Text("Nombre del jugad") },
            leadingIcon = {
                Icon(Icons.Default.Star, contentDescription = null)
            },
            isError = viewModel.userName.error != null,
            supportingText = viewModel.userName.error?.let { error ->
                { Text(error) }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .width(200.dp)
                .height(40.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6A1B9A)
            )
        ) {
            Text(text = "Iniciar Juego")
        }

        Spacer(modifier = Modifier.height(15.dp))

    }

}