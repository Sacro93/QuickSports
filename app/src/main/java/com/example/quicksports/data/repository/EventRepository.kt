package com.example.quicksports.data.repository


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.quicksports.data.models.Event
import com.example.quicksports.data.storage.EventStorage

class EventRepository(context: Context) {

    private val storage = EventStorage(context)


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getEvents(): List<Event> {
        return storage.loadEvents()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun deleteEvent(index: Int) {
        val events = getEvents().toMutableList()
        if (index in events.indices) {
            events.removeAt(index)
            storage.saveEvents(events)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveEvent(event: Event) {
        val events = getEvents().toMutableList()
        events.add(event)
        storage.saveEvents(events)
    }


}
