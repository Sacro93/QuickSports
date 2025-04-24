package com.example.quicksports.data.storage

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.quicksports.data.defaulData.LocalDateTimeSerializer
import com.example.quicksports.data.models.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.io.File
import java.time.LocalDateTime

class EventStorage(private val context: Context) {

    private val fileName = "eventos.json"

    @RequiresApi(Build.VERSION_CODES.O)
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            contextual(LocalDateTime::class, LocalDateTimeSerializer)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveEvents(events: List<Event>) {
        withContext(Dispatchers.IO) {
            val jsonString = json.encodeToString(events)
            getFile().writeText(jsonString)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun loadEvents(): List<Event> {
        return withContext(Dispatchers.IO) {
            val file = getFile()
            if (!file.exists()) return@withContext emptyList()
            val jsonString = file.readText()
            return@withContext try {
                json.decodeFromString(jsonString)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }
}
