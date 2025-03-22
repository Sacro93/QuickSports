package com.example.quicksports.presentation.ViewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.models.Sport
import com.example.quicksports.data.repository.SportsRepository
import com.example.quicksports.data.defaulData.DefaultSports
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SportsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SportsRepository(application.applicationContext)

    private val _sports = MutableStateFlow<List<Sport>>(emptyList())
    val sports: StateFlow<List<Sport>> = _sports

    init {
        viewModelScope.launch {
            cargarSiVacioSports()
        }
    }

    private suspend fun cargarSiVacioSports() {
        val cargados = repository.obtenerSports()
        if (cargados.isEmpty()) {
            val porDefecto = DefaultSports.get()
            repository.guardarSports(porDefecto)
            _sports.value = porDefecto
        } else {
            _sports.value = cargados
        }
    }
}
