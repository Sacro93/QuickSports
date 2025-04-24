package com.example.quicksports.presentation.ViewModel.Center

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.defaulData.DefaultCenters
import com.example.quicksports.data.models.Center
import com.example.quicksports.data.repository.CenterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CenterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CenterRepository(application.applicationContext)

    private val _centers = MutableStateFlow<List<Center>>(emptyList())
    val centers: StateFlow<List<Center>> = _centers

    init {
        viewModelScope.launch {
            loadYesEmptyCenter()
        }
    }

    private suspend fun loadYesEmptyCenter() {
        val loaded = repository.getCenters()
        if (loaded.isEmpty()) {
            val byDefault = DefaultCenters.get()
            repository.saveCenter(byDefault)
            _centers.value = byDefault
        } else {
            _centers.value = loaded
        }
    }
    fun toggleFavorite(centers: Center) {
        viewModelScope.launch {
            val new = _centers.value.map {
                if (it.id == centers.id) it.copy(isFavorite = !it.isFavorite) else it
            }
            repository.saveCenter(new)
            _centers.value = new
        }
    }

    fun resetCenters() {
        viewModelScope.launch {
            val default = DefaultCenters.get()
            repository.saveCenter(default)
            _centers.value = default
        }
    }


}