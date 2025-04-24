package com.example.quicksports.presentation.ViewModel.Sports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.models.Sport
import com.example.quicksports.data.repository.SportsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SportsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SportsRepository(application.applicationContext)

    private val _sports = MutableStateFlow<List<Sport>>(emptyList())
    val sports: StateFlow<List<Sport>> = _sports

    init {
        viewModelScope.launch {
            val loaded = repository.getSports()
            if (loaded.isEmpty()) {
                val default = repository.getDefaultSports()
                repository.saveSports(default)
                _sports.value = default
            } else {
                _sports.value = loaded
            }
        }
    }
    fun loadSportsIfEmpty() {
        viewModelScope.launch {
            if (_sports.value.isEmpty()) {
                val loaded = repository.getSports()
                if (loaded.isEmpty()) {
                    val default = repository.getDefaultSports()
                    repository.saveSports(default)
                    _sports.value = default
                } else {
                    _sports.value = loaded
                }
            }
        }
    }

}