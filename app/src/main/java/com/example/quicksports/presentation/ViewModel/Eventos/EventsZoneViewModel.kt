package com.example.quicksports.presentation.ViewModel.Eventos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.quicksports.data.models.EventZone
import com.example.quicksports.data.repository.EventsZoneRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@RequiresApi(Build.VERSION_CODES.O)
class EventsZoneViewModel(repository1: EventsZoneRepository) : ViewModel() {
    private val repository = EventsZoneRepository()

    private val _eventsZone = MutableStateFlow<List<EventZone>>(emptyList())
    val eventsZone: StateFlow<List<EventZone>> = _eventsZone

    init {
        _eventsZone.value = repository.getEventsZone()
    }
}