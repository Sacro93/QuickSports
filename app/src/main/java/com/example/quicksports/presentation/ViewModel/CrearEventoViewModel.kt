package com.example.quicksports.presentation.ViewModel

import androidx.lifecycle.ViewModel
import com.example.quicksports.data.Repository.CenterRepository
import com.example.quicksports.data.models.Center
import com.example.quicksports.data.models.Evento
import com.example.quicksports.data.models.Friend
import com.example.quicksports.data.models.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime


class CrearEventoViewModel : ViewModel() {

    private val repository = CenterRepository()

    private val _selectedSport = MutableStateFlow<Sport?>(null)
    val selectedSport = _selectedSport.asStateFlow()

    private val _centers = MutableStateFlow<List<Center>>(emptyList())
    val centers = _centers.asStateFlow()

    private val _selectedCenter = MutableStateFlow<Center?>(null)
    val selectedCenter = _selectedCenter.asStateFlow()

    private val _fechaHora = MutableStateFlow<LocalDateTime?>(null)
    val fechaHora = _fechaHora.asStateFlow()

    private val _maxParticipantes = MutableStateFlow(0)
    val maxParticipantes = _maxParticipantes.asStateFlow()

    private val _amigosInvitados = MutableStateFlow<List<Friend>>(emptyList())
    val amigosInvitados = _amigosInvitados.asStateFlow()

    init {
        loadCenters()
    }

    private fun loadCenters() {
        _centers.value = repository.getCenters()
    }

    fun selectSport(sport: Sport) {
        _selectedSport.value = sport
    }

    fun selectCenter(center: Center) {
        _selectedCenter.value = center
    }

    fun updateFechaHora(fechaHora: LocalDateTime) {
        _fechaHora.value = fechaHora
    }

    fun updateMaxParticipantes(cantidad: Int) {
        _maxParticipantes.value = cantidad
    }

    fun updateAmigosInvitados(lista: List<Friend>) {
        _amigosInvitados.value = lista
    }

    fun getFilteredCenters(): List<Center> {
        val sportId = _selectedSport.value?.id ?: return emptyList()
        return _centers.value.filter { it.sportPrices.containsKey(sportId) }
    }

    fun armarEvento(): Evento? {
        val deporte = _selectedSport.value
        val centro = _selectedCenter.value
        val fecha = _fechaHora.value

        if (deporte != null && centro != null && fecha != null) {
            return Evento(
                deporte = deporte,
                centro = centro,
                fechaHora = fecha,
                maxParticipantes = _maxParticipantes.value,
                amigosInvitados = _amigosInvitados.value
            )
        }
        return null
    }
    fun isCentroSeleccionado(center: Center): Boolean {
        return _selectedCenter.value?.id == center.id
    }
}

