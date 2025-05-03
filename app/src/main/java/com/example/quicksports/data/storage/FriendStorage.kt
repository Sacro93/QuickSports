package com.example.quicksports.data.storage

import android.content.Context
import com.example.quicksports.data.models.Friend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class FriendStorage(private val context: Context) {

    private val fileName = "friends.json"

    //Este es el método que realmente escribe el archivo friends.json en la memoria del dispositivo.
    suspend fun saveFriends(friends: List<Friend>) {
        withContext(Dispatchers.IO) {
            val json = Json.encodeToString(friends)
            File(context.filesDir, fileName).writeText(json)
        }
    }

    suspend fun loadFriends(): List<Friend> {
        return withContext(Dispatchers.IO) {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) return@withContext emptyList()
            val json = file.readText()
            return@withContext Json.decodeFromString(json)
        }
    }
}