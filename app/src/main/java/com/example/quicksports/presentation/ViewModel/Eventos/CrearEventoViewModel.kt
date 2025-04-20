package com.example.quicksports.presentation.ViewModel.Eventos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.models.Center
import com.example.quicksports.data.models.Evento
import com.example.quicksports.data.models.Friend
import com.example.quicksports.data.models.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

class CrearEventoViewModel : ViewModel() {

    private val _selectedSport = MutableStateFlow<Sport?>(null)
    val selectedSport: StateFlow<Sport?> = _selectedSport

    private val _selectedCenter = MutableStateFlow<Center?>(null)
    val selectedCenter: StateFlow<Center?> = _selectedCenter

    private val _fechaHora = MutableStateFlow<LocalDateTime?>(null)
    val fechaHora: StateFlow<LocalDateTime?> = _fechaHora

    private val _maxParticipantes = MutableStateFlow(0)
    val maxParticipantes: StateFlow<Int> = _maxParticipantes

    private val _amigosInvitados = MutableStateFlow<List<Friend>>(emptyList())
    val amigosInvitados: StateFlow<List<Friend>> = _amigosInvitados


    fun getFilteredCenters(centros: List<Center>): StateFlow<List<Center>> {
        return selectedSport.map { sport ->
            sport?.let { s -> centros.filter { it.sportPrices.containsKey(s.id) } } ?: emptyList()
        }.stateIn(viewModelScope, SharingStarted.Companion.Eagerly, emptyList())
    }

    fun selectSport(sport: Sport) {
        _selectedSport.value = sport
        _selectedCenter.value = null
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun armarEvento(): Evento? {
        val deporte = _selectedSport.value
        val centro = _selectedCenter.value
        val fecha = _fechaHora.value

        return if (deporte != null && centro != null && fecha != null) {
            Evento(
                deporte = deporte,
                centro = centro,
                fechaHora = fecha,
                maxParticipantes = _maxParticipantes.value,
                amigosInvitados = _amigosInvitados.value,
                creationTime = LocalDateTime.now()
            )
        } else {
            null
        }
    }


    fun isCentroSeleccionado(center: Center): Boolean {
        return _selectedCenter.value?.id == center.id
    }

    fun reset() {
        _selectedSport.value = null
        _selectedCenter.value = null
        _fechaHora.value = null
        _maxParticipantes.value = 0
        _amigosInvitados.value = emptyList()
    }

}