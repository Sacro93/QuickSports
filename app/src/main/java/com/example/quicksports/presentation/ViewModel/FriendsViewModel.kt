package com.example.quicksports.presentation.ViewModel

import androidx.lifecycle.ViewModel
import com.example.quicksports.data.Repository.FriendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.quicksports.data.models.Friend
import com.example.quicksports.data.models.Sport

class FriendsViewModel(
    private val repository: FriendsRepository = FriendsRepository()
) : ViewModel() {

    private val _friends = MutableStateFlow<List<Friend>>(emptyList())
    val friends = _friends.asStateFlow()

    private val _selectedFriends = MutableStateFlow(setOf<Friend>())
    val selectedFriends = _selectedFriends.asStateFlow()

    private val _selectedSport = MutableStateFlow<Sport?>(null)
    val selectedSport = _selectedSport.asStateFlow()

    init {
        loadFriends()
    }

    fun loadFriends() {
        _friends.value = repository.getFriends()
    }

    fun toggleFriendSelection(friend: Friend) {
        _selectedFriends.value = if (_selectedFriends.value.contains(friend)) {
            _selectedFriends.value - friend
        } else {
            _selectedFriends.value + friend
        }
    }

    fun selectSport(sport: Sport) {
        _selectedSport.value = sport
    }
}
