package com.example.quicksports.data.models
import kotlinx.serialization.Serializable

@Serializable
data class Sport(
    val id: Int,
    val name: String,
    @Transient val imageRes: Int = 0 // Ignorado en la serialización, valor por defecto 0
)
