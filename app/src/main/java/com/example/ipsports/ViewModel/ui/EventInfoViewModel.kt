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

     fun getSportName(sportId: String): String {
        return _sports.value.find { it.id == sportId }?.name ?: "Desconocido"
    }

     fun getCenterName(centerId: String): String {
        return _centersWithSports.value.find { it.center.id == centerId }?.center?.name ?: "Desconocido"
    }

    /** Filtra los centros que tienen el deporte seleccionado */
    fun loadCentersForSport(sportId: String) {
        if (_centersWithSports.value.isEmpty()) {
            viewModelScope.launch {
                val allCenters = centerRepository.getCentersWithSports()
                _centersWithSports.value = allCenters.filter { it.sports.any { sport -> sport.id == sportId } }
            }
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

    /** Actualiza el número máximo de participantes (Manteniendo como Int) */
    fun updateMaxParticipants(value: Int) {
        _uiState.update { it.copy(maxParticipants = value) }
    }

    /** Actualiza la lista de amigos invitados */
    fun updateInvitedFriends(friends: List<String>) {
        _uiState.update { it.copy(invitedFriends = friends) }
    }


}
