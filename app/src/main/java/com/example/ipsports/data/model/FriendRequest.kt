package com.example.ipsports.data.model

data class FriendRequest(
    val senderId: String = "",  // ID del usuario que envió la solicitud
    val senderName: String = "", // Nombre del usuario que envió la solicitud
    val receiverId: String = "", // ID del usuario que la recibe
    val status: String = "pending" // "pending", "accepted", "declined"
)