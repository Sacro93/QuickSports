package com.example.quicksports.data.models
import kotlinx.serialization.Serializable

@Serializable
data class Sport(val id: Int, val name: String, val imageRes: Int)
