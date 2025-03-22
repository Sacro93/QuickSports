package com.example.quicksports.data.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Evento(
    val deporte: Sport,
    val centro: Center,
    val fechaHora: LocalDateTime,
    val maxParticipantes: Int,
    val amigosInvitados: List<Friend> = emptyList()
)
