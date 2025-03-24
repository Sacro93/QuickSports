package com.example.quicksports.presentation.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.models.RegisterUiState
import com.example.quicksports.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(name = value)
    }

    fun onLastNameChange(value: String) {
        _uiState.value = _uiState.value.copy(lastName = value)
    }

    fun onTelefonoChanged(value: String) {
        _uiState.value = _uiState.value.copy(telefono = value)
    }

    fun onDomicilioChanged(value: String) {
        _uiState.value = _uiState.value.copy(domicilio = value)
    }

    fun onFechaNacimientoChanged(value: String) {
        _uiState.value = _uiState.value.copy(fechaNacimiento = value)
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value.trim())
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(password = value.trim())
    }

    fun onRepeatPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = value.trim())
    }

    fun onRegisterClick(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val state = _uiState.value

        if (!isValidPassword(state.password)) {
            onError("La contraseña debe tener al menos 8 caracteres, una mayúscula y un número.")
            return
        }

        if (state.password != state.confirmPassword) {
            onError("Las contraseñas no coinciden.")
            return
        }

        viewModelScope.launch {
            val result = repository.registerUser(state.email, state.password)
            if (result.isSuccess) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.sendEmailVerification()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Error al enviar email de verificación.")
                    }
                }
            } else {
                onError(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }


    private fun isValidPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*\\d).{8,}$")
        return password.matches(regex)
    }
}

