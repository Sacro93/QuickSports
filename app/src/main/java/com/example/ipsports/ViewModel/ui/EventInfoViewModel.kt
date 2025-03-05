package com.example.ipsports.ViewModel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ipsports.data.model.Center
import com.example.ipsports.data.model.CenterWithSports
import com.example.ipsports.data.model.EventInfoUiState
import com.example.ipsports.data.model.Sport
import com.example.ipsports.data.repository.CenterRepository
import com.example.ipsports.data.repository.SportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EventInfoViewModel @Inject constructor(
    private val sportRepository: SportRepository,
    private val centerRepository: CenterRepository
) : ViewModel() {

    private val _sports = MutableStateFlow<List<Sport>>(emptyList())
    val sports: StateFlow<List<Sport>> = _sports.asStateFlow()

    private val _centersWithSports = MutableStateFlow<List<CenterWithSports>>(emptyList())
    val centersWithSports: StateFlow<List<CenterWithSports>> = _centersWithSports.asStateFlow()

    private val _uiState = MutableStateFlow(EventInfoUiState())
    val uiState: StateFlow<EventInfoUiState> = _uiState.asStateFlow()

    init {
        loadSports()
        loadCenters()
    }

    /** Carga la lista de deportes disponibles */
    fun loadSports() {
        viewModelScope.launch {
            _sports.value = sportRepository.getSports()
        }
    }

    /** Carga todos los centros deportivos */
    fun loadCenters() {
        viewModelScope.launch {
            _centersWithSports.value = centerRepository.getCentersWithSports()
        }
    }

    /** Filtra los centros que tienen el deporte seleccionado */
    fun loadCentersForSport(sportId: String) {
        viewModelScope.launch {
            val allCenters = centerRepository.getCentersWithSports()
            val filteredCenters = allCenters.filter { it.sports.any { sport -> sport.id == sportId } }

            if (filteredCenters.isEmpty()) {
                println("⚠️ No hay centros disponibles para el deporte con ID: $sportId")
            } else {
                println("✅ Centros disponibles para el deporte $sportId: ${filteredCenters.map { it.center.name }}")
            }

            _centersWithSports.value = filteredCenters
        }
    }

    /** Actualiza la fecha seleccionada */
    fun updateDate(date: Date) {
        _uiState.update { it.copy(dateTime = date) }
    }

    /** Actualiza el centro seleccionado */
    fun updateSelectedCenter(center: Center) {
        _uiState.update { it.copy(selectedCenter = center) }
    }

    /** Actualiza el número máximo de participantes */
    fun updateMaxParticipants(value: String) {
        _uiState.update { it.copy(maxParticipants = value) }
    }

    /** Actualiza la lista de amigos invitados */
    fun updateInvitedFriends(friends: List<String>) {
        _uiState.update { it.copy(invitedFriends = friends) }
    }
}
