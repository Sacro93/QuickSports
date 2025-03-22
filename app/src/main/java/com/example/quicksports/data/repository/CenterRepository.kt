package com.example.quicksports.data.repository

import android.content.Context
import com.example.quicksports.data.models.Center
import com.example.quicksports.data.storage.CenterStorage

class CenterRepository(context: Context) {

    private val storage = CenterStorage(context)

    suspend fun obtenerCentros(): List<Center> {
        return storage.loadCenters()
    }

    suspend fun guardarCentros(centros: List<Center>) {
        storage.saveCenters(centros)
    }
}
