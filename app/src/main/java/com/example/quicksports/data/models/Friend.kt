package com.example.quicksports.data.models
import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val name: String,
    val phone: String,
    val avatar: Int,
    val age: Int,
    val deportesFavoritos: List<String> = emptyList()

)
