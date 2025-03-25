package com.example.quicksports.presentation.ViewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.R
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
            // Asignar imágenes por ID
            val conImagenes = cargados.map { sport ->
                sport.copy(
                    imageRes = when (sport.id) {
                        1 -> R.drawable.futbol
                        2 -> R.drawable.basquet
                        3 -> R.drawable.tenis
                        4 -> R.drawable.padel
                        else -> 0
                    }
                )
            }
            _sports.value = conImagenes
        }
    }

}
