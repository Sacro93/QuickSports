package com.example.quicksports.data.models
import kotlinx.serialization.Serializable

@Serializable
data class Center(
    val id: Int,
    val name: String,
    val address: String,
    val contactPhone: String,
    val sportPrices: Map<Int,Int>,
    val isFavorite: Boolean = false
)
