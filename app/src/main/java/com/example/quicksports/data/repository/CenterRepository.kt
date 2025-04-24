package com.example.quicksports.data.repository

import android.content.Context
import com.example.quicksports.data.models.Center
import com.example.quicksports.data.storage.CenterStorage

class CenterRepository(context: Context) {

    private val storage = CenterStorage(context)

    suspend fun getCenters(): List<Center> {
        return storage.loadCenters()
    }

    suspend fun saveCenter(centers: List<Center>) {
        storage.saveCenters(centers)
    }
}
