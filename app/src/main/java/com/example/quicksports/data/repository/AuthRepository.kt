package com.example.quicksports.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await


class AuthRepository(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    suspend fun registerUser(email: String, password: String): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null && user.isEmailVerified) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Debes verificar tu correo electrónico."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun getCurrentUser() = auth.currentUser
    fun currentUser() = auth.currentUser

    fun logout() {
        auth.signOut()
    }
}
