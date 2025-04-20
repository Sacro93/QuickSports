package com.example.quicksports.presentation.ViewModel.User

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.models.UserProfile
import com.example.quicksports.data.repository.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _user = MutableStateFlow<UserProfile?>(null)
    val user: StateFlow<UserProfile?> = _user

    private val firestore = FirebaseFirestore.getInstance()

    fun loadCurrentUser() {
        val currentUser = authRepository.getCurrentUser()
        if (currentUser != null) {
            val uid = currentUser.uid
            viewModelScope.launch {
                try {
                    val snapshot = firestore.collection("users").document(uid).get().await()
                    _user.value = snapshot.toObject(UserProfile::class.java)
                } catch (e: Exception) {
                    _user.value = null
                }
            }
        }
    }

    fun updateUserProfile(profile: UserProfile, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                firestore.collection("users").document(profile.uid).set(profile).await()
                _user.value = profile
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun logout() {
        authRepository.logout()
    }
}