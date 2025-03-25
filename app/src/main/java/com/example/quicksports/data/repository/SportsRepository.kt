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

    suspend fun obtenerSports(): List<Sport> {
        val cargados = storage.loadSports()
        return cargados.map { sport ->
            sport.copy(imageRes = imagenesPorId[sport.id] ?: 0)
        }
    }

    suspend fun guardarSports(sports: List<Sport>) {
        storage.saveSports(sports)
    }
}
