package com.example.quicksports.data.models

data class RegisterUiState(
    val name: String = "",
    val lastName: String = "",
    val phone: String = "",
    val address: String = "",
    val dateBirth: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null,
    val success: Boolean = false,
    val favoriteSports: List<Int> = emptyList(),
)
