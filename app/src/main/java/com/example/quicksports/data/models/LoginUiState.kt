package com.example.quicksports.data.models

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val errorMessage: String? = null
)
