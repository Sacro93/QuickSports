package com.example.ipsports.ViewModel.ui
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ipsports.data.model.User
import com.example.ipsports.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _friendsList = MutableStateFlow<List<User>>(emptyList()) // 🔹 Lista de amigos
    val friendsList: StateFlow<List<User>> = _friendsList.asStateFlow()

    private val _availableUsers = MutableStateFlow<List<User>>(emptyList()) // 🔹 Usuarios disponibles
    val availableUsers: StateFlow<List<User>> = _availableUsers.asStateFlow()

    init {
        loadFriends()
        loadAvailableUsers()
    }

    /**  Carga el usuario desde Firestore */
    fun loadUser(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = userRepository.getUser(userId)
            if (result.isSuccess) {
                _user.value = result.getOrNull()
            } else {
                _errorMessage.value = "Error al cargar usuario: ${result.exceptionOrNull()?.localizedMessage}"
            }
            _isLoading.value = false
        }
    }

    /* Guarda un nuevo usuario en Firestore */
    fun saveUser(user: User) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.addUser(user)
            if (result.isSuccess) {
                val userId = result.getOrThrow()
                _user.value = user.copy(id = userId)
            } else {
                _errorMessage.value = "Error al guardar usuario"
            }
            _isLoading.value = false
        }
    }

    /* Actualiza los datos del usuario */
    fun updateUser(userId: String, user: User) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.updateUser(userId, user)
            if (result.isFailure) {
                _errorMessage.value = "Error al actualizar usuario"
            } else {
                _user.value = user
            }
            _isLoading.value = false
        }
    }

    /** 🔹 Cargar lista de amigos */
    fun loadFriends() {
        viewModelScope.launch {
            _isLoading.value = true
            val friends = userRepository.getFriends()

            if (friends.isEmpty()) {
                println("⚠️ No se encontraron amigos en Firestore")
            } else {
                println("✅ Amigos encontrados: ${friends.map { it.name }}")
            }

            _friendsList.value = friends
            _isLoading.value = false
        }
    }

    /** 🔹 Cargar usuarios disponibles */
    fun loadAvailableUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _availableUsers.value = userRepository.getAvailableUsers()
            _isLoading.value = false
        }
    }



}


