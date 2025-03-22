package com.example.quicksports.data.repository

import android.content.Context
import com.example.quicksports.data.models.Sport
import com.example.quicksports.data.storage.SportStorage

class SportsRepository(context: Context) {

    private val storage = SportStorage(context)

    suspend fun obtenerSports(): List<Sport> {
        return storage.loadSports()
    }

    suspend fun guardarSports(sports: List<Sport>) {
        storage.saveSports(sports)
    }
}