package com.example.quicksports.data.repository


import android.content.Context
import com.example.quicksports.data.models.Evento
import com.example.quicksports.data.storage.EventoStorage

class EventoRepository(context: Context) {

    private val storage = EventoStorage(context)

//    suspend fun guardarEvento(evento: Evento) {
//        val eventos = obtenerEventos().toMutableList()
//        eventos.add(evento)
//        storage.saveEventos(eventos)
//    }

    suspend fun obtenerEventos(): List<Evento> {
        return storage.loadEventos()
    }

    suspend fun eliminarEvento(index: Int) {
        val eventos = obtenerEventos().toMutableList()
        if (index in eventos.indices) {
            eventos.removeAt(index)
            storage.saveEventos(eventos)
        }
    }

//    suspend fun eliminarTodos() {
//        storage.saveEventos(emptyList())
//    }
}
