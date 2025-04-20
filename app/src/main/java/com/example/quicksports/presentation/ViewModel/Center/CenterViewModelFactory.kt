package com.example.quicksports.presentation.ViewModel.Center

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CenterViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CenterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CenterViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
