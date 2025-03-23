package com.example.quicksports.data.storage


import android.content.Context
import com.example.quicksports.data.models.Sport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File
import kotlinx.serialization.serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer


class SportStorage(private val context: Context) {

    private val fileName = "sports.json"

    suspend fun saveSports(sports: List<Sport>) {
        withContext(Dispatchers.IO) {
            val json = Json.encodeToString(ListSerializer(Sport.serializer()), sports)
            File(context.filesDir, fileName).writeText(json)
        }
    }

    suspend fun loadSports(): List<Sport> {
        return withContext(Dispatchers.IO) {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) return@withContext emptyList()
            val json = file.readText()
            Json.decodeFromString(ListSerializer(Sport.serializer()), json)
        }
    }
}

