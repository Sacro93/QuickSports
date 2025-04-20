package com.example.quicksports.presentation.ViewModel.Friends

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quicksports.data.defaulData.DefaultFriends
import com.example.quicksports.data.models.Friend
import com.example.quicksports.data.repository.FriendsRepository
import kotlinx.coroutines.flow.MutableStateFlow
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

    fun loadFriends() {
        viewModelScope.launch {
            cargarSiVacioFriends()
        }
    }

    fun eliminarAmigo(phone: String) {
        viewModelScope.launch {
            val actual = repository.obtenerAmigos()
            val nuevos = actual.filter { it.phone != phone }
            repository.guardarAmigos(nuevos)
            _friends.value = nuevos
        }
    }
    fun reemplazarAmigos(nuevos: List<Friend>) {
        viewModelScope.launch {
            repository.guardarAmigos(nuevos)
            _friends.value = nuevos
        }
    }


}