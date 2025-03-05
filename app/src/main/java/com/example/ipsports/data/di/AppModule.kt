package com.example.ipsports.data.di

import com.example.ipsports.data.Auth.AuthRepository
import com.example.ipsports.data.Usecase.LoginUseCase
import com.example.ipsports.data.Usecase.RegisterUseCase
import com.example.ipsports.data.repository.CenterRepository
import com.example.ipsports.data.repository.EventRepository
import com.example.ipsports.data.repository.SportRepository
import com.example.ipsports.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    // ✅ Proveer FirebaseAuth
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    // ✅ Proveer Firestore
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    // ✅ Proveer Repositorios
    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepository(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore): UserRepository {
        return UserRepository(firestore,FirebaseAuth.getInstance())
    }

    @Provides
    @Singleton
    fun provideSportRepository(firestore: FirebaseFirestore): SportRepository {
        return SportRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideCourtRepository(firestore: FirebaseFirestore): CenterRepository {
        return CenterRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideEventRepository(firestore: FirebaseFirestore): EventRepository {
        return EventRepository(firestore)
    }

    // ✅ Proveer Use Cases con dependencias correctas
    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository, userRepository: UserRepository): RegisterUseCase {
        return RegisterUseCase(authRepository, userRepository)
    }

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }
}

