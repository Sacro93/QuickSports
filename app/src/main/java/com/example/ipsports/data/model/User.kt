package com.example.ipsports.data.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String = "", // 🔹 Agregar `@DocumentId` para que Firestore lo maneje automáticamente
    val email: String = "",
    val name: String = "",
    val surname: String = "",
    val location: String = "",
    val verified: Boolean = false,
    val profileImageUrl: String? = null
)