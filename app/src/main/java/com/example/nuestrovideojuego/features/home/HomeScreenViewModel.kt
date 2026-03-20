package com.example.nuestrovideojuego.features.home

import androidx.lifecycle.ViewModel
import com.example.myapplication.core.utils.ValidatedField

class HomeScreenViewModel: ViewModel() {

    val userName = ValidatedField("") { value ->
        when {
            value.isEmpty() -> "El nombre del usuario es requerido"
            else -> null
        }
    }


}