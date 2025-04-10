package com.example.quicksports.presentation.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.defaulData.DefaultCenters
import com.example.quicksports.data.repository.CenterRepository
import com.example.quicksports.data.models.Center
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

    class CenterViewModel(application: Application) : AndroidViewModel(application) {

        private val repository = CenterRepository(application.applicationContext)

        private val _centros = MutableStateFlow<List<Center>>(emptyList())
        val centros: StateFlow<List<Center>> = _centros

        init {
            viewModelScope.launch {
                cargarSiVacioCenter()
            }
        }

        private suspend fun cargarSiVacioCenter() {
            val cargados = repository.obtenerCentros()
            if (cargados.isEmpty()) {
                val porDefecto = DefaultCenters.get()
                repository.guardarCentros(porDefecto)
                _centros.value = porDefecto
            } else {
                _centros.value = cargados
            }
        }
        fun toggleFavorite(centro: Center) {
            viewModelScope.launch {
                val nuevos = _centros.value.map {
                    if (it.id == centro.id) it.copy(isFavorite = !it.isFavorite) else it
                }
                repository.guardarCentros(nuevos)
                _centros.value = nuevos
            }
        }

    }