package com.example.quicksports.presentation.ViewModel.User

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quicksports.data.repository.AuthRepository

class UserViewModelFactory(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
