package com.example.quicksports.data.models

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val keepLoggedIn: Boolean = false, // ✅ ESTE ES EL QUE FALTABA
    val errorMessage: String? = null
)
