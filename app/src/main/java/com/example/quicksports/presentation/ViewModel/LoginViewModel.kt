package com.example.quicksports.presentation.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.models.LoginUiState
import com.example.quicksports.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value.trim())
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value.trim())
    }

    fun onRememberMeChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(rememberMe = value)
    }

    fun onLoginClick(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val state = _uiState.value

        viewModelScope.launch {
            val result = repository.login(state.email, state.password)
            if (result.isSuccess) {
                onSuccess()
            } else {
                _uiState.value = _uiState.value.copy(errorMessage = result.exceptionOrNull()?.message)
                onError(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }
}
