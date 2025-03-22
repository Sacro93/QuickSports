package com.example.quicksports.data.storage

import android.content.Context
import com.example.quicksports.data.models.Center
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import kotlinx.serialization.encodeToString

class CenterStorage(private val context: Context) {

    private val fileName = "centros.json"

    suspend fun saveCenters(centers: List<Center>) {
        withContext(Dispatchers.IO) {
            val json: String = Json.encodeToString<List<Center>>(centers)
            File(context.filesDir, fileName).writeText(json)
        }
    }
    suspend fun loadCenters(): List<Center> {
        return withContext(Dispatchers.IO) {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) return@withContext emptyList()
            val json = file.readText()
            Json.decodeFromString(json)
        }
    }
}
