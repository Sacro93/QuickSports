package com.example.quicksports.data.defaulData

import com.example.quicksports.R
import com.example.quicksports.data.models.Sport

object DefaultSports {
    fun get(): List<Sport> = listOf(
        Sport(1, "Fútbol", R.drawable.futbol),
        Sport(2, "Básquet", R.drawable.basquet),
        Sport(3, "Tenis", R.drawable.tenis),
        Sport(4, "Pádel", R.drawable.padel)
    )
}