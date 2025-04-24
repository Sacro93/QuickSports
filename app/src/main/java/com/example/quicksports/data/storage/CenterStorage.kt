package com.example.quicksports.data.storage

import android.content.Context
import com.example.quicksports.data.defaulData.DefaultCenters
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

            if (!file.exists()) {
                val default = DefaultCenters.get()
                saveCenters(default)
                return@withContext default
            }

            val json = file.readText()

            return@withContext try {
                Json.decodeFromString(json)
            } catch (e: Exception) {
                val default = DefaultCenters.get()
                saveCenters(default)
                default
            }
        }
    }

}
