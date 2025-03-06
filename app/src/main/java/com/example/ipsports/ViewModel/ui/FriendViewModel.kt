package com.example.ipsports.ViewModel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ipsports.data.model.FriendRequest
import com.example.ipsports.ViewModel.ui.FriendViewModel

import com.example.ipsports.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FriendViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _friendRequests = MutableStateFlow<List<FriendRequest>>(emptyList())
    val friendRequests: StateFlow<List<FriendRequest>> = _friendRequests.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadPendingRequests()
    }

    fun loadPendingRequests() {
        viewModelScope.launch {
            _isLoading.value = true
            val requests = userRepository.getPendingFriendRequests()
            _friendRequests.value = requests
            _isLoading.value = false
        }
    }

    fun acceptRequest(senderId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val success = userRepository.acceptFriendRequest(senderId)
            if (success) loadPendingRequests()
            _isLoading.value = false
        }
    }

    fun rejectRequest(senderId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val success = userRepository.rejectFriendRequest(senderId)
            if (success) loadPendingRequests()
            _isLoading.value = false
        }
    }
}
