package com.example.quicksports.data.models


import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Event(
    val sport: Sport,
    val center: Center,
    @Contextual val dateTime: LocalDateTime,
    val maxParticipants: Int,
    val friendInvited: List<Friend> = emptyList(),

    @Contextual val creationTime: LocalDateTime
)
