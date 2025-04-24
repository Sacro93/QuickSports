package com.example.quicksports.presentation.ViewModel.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.models.RegisterUiState
import com.example.quicksports.data.models.UserProfile
import com.example.quicksports.data.repository.AuthRepository
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

    fun onPhoneChanged(value: String) {
        _uiState.value = _uiState.value.copy(phone = value)
    }

    fun onAddresChanged(value: String) {
        _uiState.value = _uiState.value.copy(address = value)
    }

    fun onDateBirthChanged(value: String) {
        _uiState.value = _uiState.value.copy(
            dateBirth = formatearFecha(value)
        )
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
        val validationError = validateForm(state)
        if (validationError != null) {
            onError(validationError)
            return
        }

        viewModelScope.launch {
            val profile = UserProfile(
                uid = "",
                name = state.name,
                lastName = state.lastName,
                phone = state.phone,
                address = state.address,
                dateBirth = state.dateBirth,
                email = state.email,
                favoriteSports = state.favoriteSports
            )

            val result = repository.registerUserAndSaveProfile(state.email, state.password, profile)
            if (result.isSuccess) {
                onSuccess()
            } else {
                onError(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }

    private fun formatearFecha(fecha: String): String {
        val soloNumeros = fecha.filter { it.isDigit() }
        val builder = StringBuilder()

        for (i in soloNumeros.indices) {
            if (i == 2 || i == 4) builder.append("/")
            builder.append(soloNumeros[i])
        }

        return builder.toString().take(10)
    }

    private fun isValidPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*\\d).{8,}$")
        return password.matches(regex)
    }

    fun onDeporteFavoritoToggle(id: Int) {
        val current = _uiState.value.favoriteSports.toMutableList()
        if (current.contains(id)) {
            current.remove(id)
        } else {
            current.add(id)
        }
        _uiState.value = _uiState.value.copy(favoriteSports = current)
    }




    private fun validateForm(state: RegisterUiState): String? {
        if (state.name.isBlank()) return "El nombre no puede estar vacío."
        if (state.lastName.isBlank()) return "El apellido no puede estar vacío."
        if (!state.phone.matches(Regex("^\\d{9,15}$"))) return "Teléfono inválido."
        if (!state.email.contains("@")) return "Correo electrónico inválido."
        if (!state.dateBirth.matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) return "Fecha inválida. Usa dd/mm/aaaa."
        if (!isValidPassword(state.password)) return "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número."
        if (state.password != state.confirmPassword) return "Las contraseñas no coinciden."
        return null
    }
}