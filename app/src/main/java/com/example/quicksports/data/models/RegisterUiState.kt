package com.example.quicksports.data.models

data class RegisterUiState(
    val name: String = "",
    val lastName: String = "",
    val telefono: String = "",
    val domicilio: String = "",
    val fechaNacimiento: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null,
    val success: Boolean = false,
    val deportesFavoritos: List<Int> = emptyList(),
)
