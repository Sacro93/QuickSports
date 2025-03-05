package com.example.ipsports.data.model


import java.util.Date

data class EventInfoUiState(
    val dateTime: Date? = null,
    val selectedCenter: Center? = null,
    val maxParticipants: String = "1",
    val invitedFriends: List<String> = emptyList()
)
