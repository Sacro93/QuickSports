package com.example.ipsports.data.DatosDefault

import com.example.ipsports.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun populateUsers(firestore: FirebaseFirestore) {
    val usersCollection = firestore.collection("users")

    val users = listOf(
        User(name = "Juan ",surname="Perez", location = "Barcelona", verified = true, email = "juan@example.com"),
        User(name = "María ",surname="López",  location = "Barcelona", verified = true,email = "maria@example.com"),
        User(name = "Carlos ",surname="Gómez",  location = "Barcelona", verified = true,email = "carlos@example.com"),
        User(name = "Ana ",surname="Ramírez",  location = "Barcelona", verified = true,email = "ana@example.com")
    )

    users.forEach { user ->
        val newDocRef = usersCollection.document() // 🔹 Firestore genera el ID automático
        newDocRef.set(user).await()
        println("✅ Usuario ficticio agregado con ID: ${newDocRef.id}")
    }
}
