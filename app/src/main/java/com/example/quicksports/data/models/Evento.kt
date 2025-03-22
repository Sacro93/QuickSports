package com.example.quicksports.data.models

import java.time.LocalDateTime

data class Evento(
    val deporte: Sport,
    val centro: Center,
    val fechaHora: LocalDateTime,
    val maxParticipantes: Int,
    val amigosInvitados: List<Friend> = emptyList()
)
