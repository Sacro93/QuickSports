package com.example.quicksports.data.repository

import com.example.quicksports.data.models.UserProfile
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun registerUserAndSaveProfile(
        email: String,
        password: String,
        profile: UserProfile
    ): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.sendEmailVerification()?.await()

            val uid = result.user?.uid ?: return Result.failure(Exception("Usuario sin UID"))

            firestore.collection("users")
                .document(uid)
                .set(profile.copy(uid = uid))
                .await()

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

    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    fun getCurrentUser() = auth.currentUser

    fun logout() {
        auth.signOut()
    }
}
