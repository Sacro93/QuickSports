package com.example.quicksports.presentation.ViewModel.Eventos

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.models.Evento
import com.example.quicksports.data.repository.EventoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class TusEventosViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = EventoRepository(application.applicationContext)

    private val _eventos = MutableStateFlow<List<Evento>>(emptyList())
    val eventos: StateFlow<List<Evento>> = _eventos

    init {
        viewModelScope.launch {
            _eventos.value = repository.obtenerEventos()
        }
    }

    fun eliminarEvento(index: Int) {
        viewModelScope.launch {
            repository.eliminarEvento(index)
            _eventos.value = repository.obtenerEventos()
        }
    }
}