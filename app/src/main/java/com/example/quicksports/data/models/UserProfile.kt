package com.example.quicksports.data.models

    data class UserProfile(
        val uid: String = "",
        val name: String = "",
        val lastName: String = "",
        val phone: String = "",
        val address: String = "",
        val dateBirth: String = "",
        val email: String = "",
        val favoriteSports: List<Int> = emptyList()
    )
