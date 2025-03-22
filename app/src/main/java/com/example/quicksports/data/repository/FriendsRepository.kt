package com.example.quicksports.data.repository

import android.content.Context
import com.example.quicksports.R
import com.example.quicksports.data.models.Friend
import com.example.quicksports.data.storage.FriendStorage


class FriendsRepository(context: Context) {
    private val storage = FriendStorage(context)

    suspend fun obtenerAmigos(): List<Friend> {
        return storage.loadFriends()
    }

    suspend fun guardarAmigos(amigos: List<Friend>) {
        storage.saveFriends(amigos)
    }
}
