package com.example.quicksports.presentation.ViewModel.Friends

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.defaulData.DefaultFriends
import com.example.quicksports.data.models.Friend
import com.example.quicksports.data.repository.FriendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FriendsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FriendsRepository(application.applicationContext)

    private val _friends = MutableStateFlow<List<Friend>>(emptyList())
    val friends: StateFlow<List<Friend>> = _friends

    init {
        viewModelScope.launch {
            loadYesEmptyFriends()
        }
    }

    private suspend fun loadYesEmptyFriends() {
        val loaded = repository.getFriends()
        if (loaded.isEmpty()) {
            val default = DefaultFriends.get()
            repository.saveFriends(default)
            _friends.value = default
        } else {
            _friends.value = loaded
        }
    }

    fun loadFriends() {
        viewModelScope.launch {
            loadYesEmptyFriends()
        }
    }

    fun deleteFriend(phone: String) {
        viewModelScope.launch {
            val actual = repository.getFriends()
            val news = actual.filter { it.phone != phone }
            repository.saveFriends(news)
            _friends.value = news
        }
    }
    fun replaceFriends(news: List<Friend>) {
        viewModelScope.launch {
            repository.saveFriends(news)
            _friends.value = news
        }
    }


}