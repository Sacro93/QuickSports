package com.example.quicksports.presentation.ViewModel.Eventos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CrearEventoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CrearEventoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CrearEventoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
