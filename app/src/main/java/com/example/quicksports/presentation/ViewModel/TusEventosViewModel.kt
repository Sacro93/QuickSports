package com.example.quicksports.presentation.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.repository.EventoRepository
import com.example.quicksports.data.models.Evento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TusEventosViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = EventoRepository(application.applicationContext)

    private val _eventos = MutableStateFlow<List<Evento>>(emptyList())
    val eventos: StateFlow<List<Evento>> = _eventos

    init {
        cargarEventos()
    }

    fun cargarEventos() {
        viewModelScope.launch {
            _eventos.value = repository.obtenerEventos()
        }
    }

    fun eliminarEvento(index: Int) {
        viewModelScope.launch {
            repository.eliminarEvento(index)
            cargarEventos()
        }
    }

    fun eliminarTodos() {
        viewModelScope.launch {
            repository.eliminarTodos()
            cargarEventos()
        }
    }
}
