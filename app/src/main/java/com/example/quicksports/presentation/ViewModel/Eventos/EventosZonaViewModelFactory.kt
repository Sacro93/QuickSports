package com.example.quicksports.presentation.ViewModel.Eventos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quicksports.data.repository.EventosZonaRepository

class EventosZonaViewModelFactory(
    private val repository: EventosZonaRepository = EventosZonaRepository()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventosZonaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventosZonaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}