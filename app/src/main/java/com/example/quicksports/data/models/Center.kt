package com.example.quicksports.data.models

data class Center(
    val id: Int,
    val name: String,
    val address: String,
    val contactPhone: String,
    val sportPrices: Map<Int, Double> // Int es el ID del deporte
)
