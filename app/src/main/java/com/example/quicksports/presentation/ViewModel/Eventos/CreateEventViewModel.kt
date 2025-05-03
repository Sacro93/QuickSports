package com.example.quicksports.presentation.ViewModel.Eventos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.models.Center
import com.example.quicksports.data.models.Event
import com.example.quicksports.data.models.Friend
import com.example.quicksports.data.models.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

class CreateEventViewModel : ViewModel() {

    private val _selectedSport = MutableStateFlow<Sport?>(null)
    val selectedSport: StateFlow<Sport?> = _selectedSport

    private val _selectedCenter = MutableStateFlow<Center?>(null)
    val selectedCenter: StateFlow<Center?> = _selectedCenter

    private val _dateTime = MutableStateFlow<LocalDateTime?>(null)
    val dateTime: StateFlow<LocalDateTime?> = _dateTime

    private val _maxParticipants = MutableStateFlow(0)
    val maxParticipants: StateFlow<Int> = _maxParticipants

    private val _invitedFriends = MutableStateFlow<List<Friend>>(emptyList())
    val invitedFriends: StateFlow<List<Friend>> = _invitedFriends


    fun getFilteredCenters(centers: List<Center>): StateFlow<List<Center>> {
        return selectedSport.map { sport ->
            sport?.let { s -> centers.filter { it.sportPrices.containsKey(s.id) } } ?: emptyList()
        }.stateIn(viewModelScope, SharingStarted.Companion.Eagerly, emptyList())
    }

    fun selectSport(sport: Sport) {
        _selectedSport.value = sport
        _selectedCenter.value = null
    }

    fun selectCenter(center: Center) {
        _selectedCenter.value = center
    }

    fun updateDate(dateTime: LocalDateTime) {
        _dateTime.value = dateTime
    }

    fun updateMaxParticipants(quantity: Int) {
        _maxParticipants.value = quantity
    }

    fun updateInvitedFriends(list: List<Friend>) {
        _invitedFriends.value = list
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun settingUpAnEvent(): Event? {
        val sport = _selectedSport.value
        val center = _selectedCenter.value
        val dateTime = _dateTime.value

        return if (sport != null && center != null && dateTime != null) {
            Event(
                sport = sport,
                center = center,
                dateTime = dateTime,
                maxParticipants = _maxParticipants.value,
                friendInvited = _invitedFriends.value,
                creationTime = LocalDateTime.now()
            )
        } else {
            null
        }
    }



    fun reset() {
        _selectedSport.value = null
        _selectedCenter.value = null
        _dateTime.value = null
        _maxParticipants.value = 0
        _invitedFriends.value = emptyList()
    }

}