package com.example.quicksports.data.repository

import android.content.Context
import com.example.quicksports.R
import com.example.quicksports.data.models.Sport
import com.example.quicksports.data.storage.SportStorage

class SportsRepository(context: Context) {

    private val storage = SportStorage(context)

    private val defaultImages = mapOf(
        1 to R.drawable.futbol,
        2 to R.drawable.basquet,
        3 to R.drawable.tenis,
        4 to R.drawable.padel
    )

    private val sportsDefault = listOf(
        Sport(1, "Fútbol"),
        Sport(2, "Básquet"),
        Sport(3, "Tenis"),
        Sport(4, "Pádel")
    )

    fun getDefaultSports(): List<Sport> {
        return sportsDefault.map { it.copy(imageRes = defaultImages[it.id] ?: 0) }
    }

    suspend fun getSports(): List<Sport> {
        val load = storage.loadSports()
        return load.map { it.copy(imageRes = defaultImages[it.id] ?: 0) }
    }

    suspend fun saveSports(sports: List<Sport>) {
        storage.saveSports(sports)
    }
}
