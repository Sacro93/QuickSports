package com.example.ipsports.data.model

import com.google.firebase.Timestamp


data class Event(
    val id: String = "",
    val sportId: String = "",  // ✅ ID del deporte
    val centerId: String = "", // ✅ ID del centro deportivo
    val userId: String = "",   // ✅ ID del usuario creador
    val date: Timestamp = Timestamp.now(),
    val maxParticipants: Int = 0, // ✅ Se mantiene como `Int`
    val status: String = "pending", // "pending" | "finished"
    val usersInvited: List<String> = emptyList() // ✅ Lista de IDs de usuarios invitados
)