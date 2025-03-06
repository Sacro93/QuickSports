package com.example.ipsports.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import com.example.ipsports.data.model.User
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import android.util.Log
import com.example.ipsports.data.model.FriendRequest
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

    /** 🔹 Enviar solicitud de amistad (Usando FriendRequest) */
    suspend fun sendFriendRequest(friendEmail: String): Boolean {
        val currentUserId = auth.currentUser?.uid ?: return false
        val currentUser = getUser(currentUserId).getOrNull() ?: return false

        return try {
            // Buscar usuario por email
            val userSnapshot = usersCollection.whereEqualTo("email", friendEmail).get().await()
            val friendUser = userSnapshot.documents.firstOrNull()?.toObject(User::class.java)?.copy()
            val friendId = friendUser?.id ?: return false  // ✅ Asegurar que `friendId` no sea null antes de continuar


            if (true) {
                val friendId = friendUser.id

                // Verificar si la solicitud ya existe
                val existingRequest = usersCollection.document(friendId)
                    .collection("friend_requests")
                    .document(currentUserId)
                    .get()
                    .await()

                if (!existingRequest.exists()) {
                    // Guardar solicitud en Firestore
                    val request = FriendRequest(
                        senderId = currentUserId,
                        senderName = currentUser.name, // ✅ Guardar el nombre
                        receiverId = friendId
                    )
                    usersCollection.document(friendId)
                        .collection("friend_requests")
                        .document(currentUserId)
                        .set(request)
                        .await()
                    return true
                }
            }
            false
        } catch (e: Exception) {
            false
        }
    }

    /** 🔹 Aceptar solicitud de amistad */
    suspend fun acceptFriendRequest(senderId: String): Boolean {
        val currentUserId = auth.currentUser?.uid ?: return false
        return try {
            // Agregar a la lista de amigos de ambos usuarios
            usersCollection.document(currentUserId)
                .collection("friends")
                .document(senderId)
                .set(mapOf("friendId" to senderId))
                .await()

            usersCollection.document(senderId)
                .collection("friends")
                .document(currentUserId)
                .set(mapOf("friendId" to currentUserId))
                .await()

            // Eliminar la solicitud
            usersCollection.document(currentUserId)
                .collection("friend_requests")
                .document(senderId)
                .delete()
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }


    /** 🔹 Rechazar solicitud de amistad */
    suspend fun rejectFriendRequest(senderId: String): Boolean {
        val currentUserId = auth.currentUser?.uid ?: return false
        return try {
            usersCollection.document(currentUserId)
                .collection("friend_requests")
                .document(senderId)
                .delete()
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
                .map { it.id }

            val friends = friendIds.mapNotNull { friendId ->
                usersCollection.document(friendId).get().await().toObject(User::class.java)
            }

            println(" Amigos cargados: ${friends.map { it.name }}")
            friends
        } catch (e: Exception) {
            println(" Error al obtener amigos: ${e.localizedMessage}")
            emptyList()
        }
    }

    /** 🔹 Obtener usuarios disponibles (excluyendo al usuario actual) */
    suspend fun getAvailableUsers(): List<User> {
        val userId = auth.currentUser?.uid ?: return emptyList()
        return try {
            usersCollection.get().await()
                .documents
                .mapNotNull { doc -> doc.toObject(User::class.java)?.copy(id = doc.id) }
                .filter { it.email != auth.currentUser?.email } // 🔹 Excluye al usuario actual
        } catch (e: Exception) {
            emptyList()
        }
    }

    /** 🔹 Obtener solicitudes de amistad pendientes */
    suspend fun getPendingFriendRequests(): List<FriendRequest> {
        val currentUserId = auth.currentUser?.uid ?: return emptyList()

        return try {
            val snapshot = usersCollection.document(currentUserId)
                .collection("friend_requests")
                .whereEqualTo("status", "pending")
                .get()
                .await()

            val requests = snapshot.documents.mapNotNull { it.toObject(FriendRequest::class.java) }

            println(" Solicitudes cargadas: $requests")

            requests
        } catch (e: Exception) {
            println("⚠️ Error al obtener solicitudes de amistad: ${e.localizedMessage}")
            emptyList()
        }
    }



}

