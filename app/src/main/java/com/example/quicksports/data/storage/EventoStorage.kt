package com.example.quicksports.data.storage

import android.content.Context
import com.example.quicksports.data.models.Evento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class EventoStorage(private val context: Context) {

    private val fileName = "eventos.json"

    suspend fun saveEventos(eventos: List<Evento>) {
        withContext(Dispatchers.IO) {
            val jsonString = Json.encodeToString(eventos)
            getFile().writeText(jsonString)
        }
    }

    suspend fun loadEventos(): List<Evento> {
        return withContext(Dispatchers.IO) {
            val file = getFile()
            if (!file.exists()) return@withContext emptyList()

            val jsonString = file.readText()
            return@withContext try {
                Json.decodeFromString(jsonString)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }
}
