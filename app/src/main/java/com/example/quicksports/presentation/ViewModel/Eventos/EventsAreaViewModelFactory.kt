package com.example.quicksports.presentation.ViewModel.Eventos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EventsAreaViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateEventViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
