package com.example.nuestrovideojuego.features.home

import androidx.lifecycle.ViewModel
import com.example.nuestrovideojuego.core.utils.ValidatedField

class HomeScreenViewModel: ViewModel() {

    val userName = ValidatedField("") { value ->
        when {
            value.isEmpty() -> "El nombre del usuario es requerido"
            else -> null
        }
    }

    val isFormValid: Boolean
        get() = userName.isValid

}