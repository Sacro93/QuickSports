package com.example.quicksports.presentation.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.defaulData.DefaultFriends
import com.example.quicksports.data.repository.FriendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.quicksports.data.models.Friend
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FriendsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FriendsRepository(application.applicationContext)

    private val _friends = MutableStateFlow<List<Friend>>(emptyList())
    val friends: StateFlow<List<Friend>> = _friends

    init {
        viewModelScope.launch {
            cargarSiVacioFriends()
        }
    }

    private suspend fun cargarSiVacioFriends() {
        val cargados = repository.obtenerAmigos()
        if (cargados.isEmpty()) {
            val porDefecto = DefaultFriends.get()
            repository.guardarAmigos(porDefecto)
            _friends.value = porDefecto
        } else {
            _friends.value = cargados
        }
    }
}
