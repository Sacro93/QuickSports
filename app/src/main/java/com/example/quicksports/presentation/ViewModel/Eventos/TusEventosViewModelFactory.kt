package com.example.quicksports.presentation.ViewModel.Eventos

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TusEventosViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TusEventosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TusEventosViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
