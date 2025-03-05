package com.example.ipsports.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import com.example.ipsports.data.model.User
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

// Solo debe manejar los datos del usuario (Firestore).


class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val usersCollection = firestore.collection("users")

    /** 🔹 Guarda un nuevo usuario en Firestore con ID automático */
    suspend fun addUser(user: User): Result<String> {
        return try {
            val newUserRef = usersCollection.add(user).await()
            Result.success(newUserRef.id) // ✅ Retornamos el ID generado
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al guardar usuario: ${e.localizedMessage}")
            Result.failure(e)
        }
    }

    /** 🔹 Obtiene un usuario por su ID */
    suspend fun getUser(userId: String): Result<User?> {
        return try {
            val document = usersCollection.document(userId).get().await()
            Result.success(document.toObject(User::class.java))
        } catch (e: Exception) {
            Log.e("UserRepository", "Error al obtener usuario: ${e.localizedMessage}")
            Result.failure(e)
        }
    }

    /** 🔹 Actualiza los datos del usuario */
    suspend fun updateUser(userId: String, user: User): Result<Unit> {
        return try {
            usersCollection.document(userId).set(user, SetOptions.merge()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /** 🔹 Enviar solicitud de amistad */
    suspend fun sendFriendRequest(friendId: String): Boolean {
        val userId = auth.currentUser?.uid ?: return false
        return try {
            usersCollection.document(friendId)
                .collection("friend_requests")
                .document(userId)
                .set(mapOf("status" to "pending"))
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /** 🔹 Obtener lista de amigos del usuario actual */
    suspend fun getFriends(): List<User> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return try {
            val friendIds = usersCollection.document(userId)
                .collection("friends")
                .get()
                .await()
                .documents
                .map { it.id } // 🔹 Obtener solo los IDs de los amigos

            friendIds.mapNotNull { friendId ->
                usersCollection.document(friendId).get().await().toObject(User::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /** 🔹 Obtener usuarios disponibles (excluyendo al usuario actual) */
    suspend fun getAvailableUsers(): List<User> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return try {
            usersCollection.get().await()
                .documents
                .mapNotNull { it.toObject(User::class.java)?.copy() }
                .filter { it.email != auth.currentUser?.email } // 🔹 Excluye al usuario actual
        } catch (e: Exception) {
            emptyList()
        }
    }
}

