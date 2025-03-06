package com.example.ipsports.data.repository


import com.example.ipsports.data.model.Event
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val eventsCollection = firestore.collection("events")

    /** Agrega un evento */
    suspend fun addEvent(event: Event): Result<String> {
        return try {
            val documentRef = eventsCollection.add(event).await()
            println("Evento agregado con ID: ${documentRef.id}")
            Result.success(documentRef.id)
        } catch (e: Exception) {
            println(" Error al agregar evento: ${e.localizedMessage}")
            Result.failure(e)
        }
    }

    /** Obtiene el evento más reciente del usuario */
    suspend fun getLatestEvent(userId: String): Event? {
        return try {
            val result = eventsCollection
                .whereEqualTo("userId", userId)
                .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            val event = result.documents.firstOrNull()?.toObject(Event::class.java)
            println(" Último evento: ${event?.id}")
            event
        } catch (e: Exception) {
            println(" Error al obtener último evento: ${e.localizedMessage}")
            null
        }
    }
    /** 🔹 Obtiene todos los eventos de un usuario específico */
    suspend fun getEventsByUser(userId: String): List<Event> {
        return try {
            val result = eventsCollection
                .whereEqualTo("userId", userId)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()

            result.documents.mapNotNull { it.toObject(Event::class.java)?.copy(id = it.id) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}



