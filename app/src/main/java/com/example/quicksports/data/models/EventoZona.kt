package com.example.quicksports.data.models

import kotlinx.serialization.Contextual
import java.time.LocalDateTime


import kotlinx.serialization.Serializable

@Serializable
data class EventoZona(
    val deporte: String,
    val centro: String,
    val direccion: String,
    val creador: String,
  @Contextual  val fechaHora: LocalDateTime
)