package com.example.quicksports.data.models

    data class UserProfile(
        val uid: String = "",
        val name: String = "",
        val lastName: String = "",
        val telefono: String = "",
        val domicilio: String = "",
        val fechaNacimiento: String = "",
        val email: String = "",
        val deportesFavoritos: List<Int> = emptyList()
    )
