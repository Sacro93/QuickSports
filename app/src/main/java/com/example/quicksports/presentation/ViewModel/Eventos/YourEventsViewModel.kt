package com.example.quicksports.presentation.ViewModel.Eventos

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.models.Event
import com.example.quicksports.data.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class YourEventsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = EventRepository(application.applicationContext)

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        viewModelScope.launch {
            _events.value = repository.getEvents()
        }
    }

    fun deleteEvent(index: Int) {
        viewModelScope.launch {
            repository.deleteEvent(index)
            _events.value = repository.getEvents()
        }
    }
}