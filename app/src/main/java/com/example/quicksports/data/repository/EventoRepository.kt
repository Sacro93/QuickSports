package com.example.quicksports.data.repository


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.quicksports.data.models.Evento
import com.example.quicksports.data.storage.EventoStorage

class EventoRepository(context: Context) {

    private val storage = EventoStorage(context)


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun obtenerEventos(): List<Evento> {
        return storage.loadEventos()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun eliminarEvento(index: Int) {
        val eventos = obtenerEventos().toMutableList()
        if (index in eventos.indices) {
            eventos.removeAt(index)
            storage.saveEventos(eventos)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun guardarEvento(evento: Evento) {
        val eventos = obtenerEventos().toMutableList()
        eventos.add(evento)
        storage.saveEventos(eventos)
    }

//    suspend fun eliminarTodos() {
//        storage.saveEventos(emptyList())
//    }
}
