package com.example.quicksports.presentation.ViewModel.Eventos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quicksports.data.repository.EventsZoneRepository

class EventAreaViewModelFactory(
    private val repository: EventsZoneRepository = EventsZoneRepository()
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventsZoneViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventsZoneViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}