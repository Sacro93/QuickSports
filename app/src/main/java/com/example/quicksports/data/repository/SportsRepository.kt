package com.example.quicksports.data.repository

import android.content.Context
import com.example.quicksports.R
import com.example.quicksports.data.models.Sport
import com.example.quicksports.data.storage.SportStorage

class SportsRepository(context: Context) {

    private val storage = SportStorage(context)

    private val imagenesPorId = mapOf(
        1 to R.drawable.futbol,
        2 to R.drawable.basquet,
        3 to R.drawable.tenis,
        4 to R.drawable.padel
    )

    private val deportesPorDefecto = listOf(
        Sport(1, "Fútbol"),
        Sport(2, "Básquet"),
        Sport(3, "Tenis"),
        Sport(4, "Pádel")
    )

    fun obtenerDeportesPorDefecto(): List<Sport> {
        return deportesPorDefecto.map { it.copy(imageRes = imagenesPorId[it.id] ?: 0) }
    }

    suspend fun obtenerSports(): List<Sport> {
        val cargados = storage.loadSports()
        return cargados.map { it.copy(imageRes = imagenesPorId[it.id] ?: 0) }
    }

    suspend fun guardarSports(sports: List<Sport>) {
        storage.saveSports(sports)
    }
}
