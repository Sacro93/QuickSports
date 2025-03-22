package com.example.quicksports.presentation.ViewModel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.quicksports.data.Repository.EventosZonaRepository
import com.example.quicksports.data.models.EventoZona
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@RequiresApi(Build.VERSION_CODES.O)
class EventosZonaViewModel : ViewModel() {
    private val repository = EventosZonaRepository()

    private val _eventosZona = MutableStateFlow<List<EventoZona>>(emptyList())
    val eventosZona: StateFlow<List<EventoZona>> = _eventosZona

    init {
        _eventosZona.value = repository.getEventosZona()
    }
}
