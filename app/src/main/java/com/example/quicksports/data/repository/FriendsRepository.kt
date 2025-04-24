package com.example.quicksports.data.repository

import android.content.Context
import com.example.quicksports.R
import com.example.quicksports.data.models.Friend
import com.example.quicksports.data.storage.FriendStorage


class FriendsRepository(context: Context) {
    private val storage = FriendStorage(context)

    suspend fun getFriends(): List<Friend> {
        return storage.loadFriends()
    }

    suspend fun saveFriends(friend: List<Friend>) {
        storage.saveFriends(friend)
    }
}
