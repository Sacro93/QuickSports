package com.example.quicksports.presentation.ViewModel.Sports

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SportsViewModelFactory(
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SportsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SportsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
