package com.example.ipsports.data.model

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val surname: String = "",
    val location: String = "",
    val verified: Boolean = false,
    val profileImageUrl: String? = null
)
