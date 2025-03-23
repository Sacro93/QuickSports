package com.example.quicksports.presentation.Screens.s


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.data.models.Friend
import com.example.quicksports.presentation.ViewModel.CrearEventoViewModel
import com.example.quicksports.presentation.ViewModel.FriendsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FriendSelectorScreen(
    navController: NavController,
    crearEventoViewModel: CrearEventoViewModel,
    friendsViewModel: FriendsViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val friends by friendsViewModel.friends.collectAsState()
    val amigosInvitados by crearEventoViewModel.amigosInvitados.collectAsState()

    val selectedFriends = remember { mutableStateListOf<String>() }

    LaunchedEffect(amigosInvitados) {
        selectedFriends.clear()
        selectedFriends.addAll(amigosInvitados.map { it.phone }) // usar teléfono como identificador
    }

    var searchQuery by remember { mutableStateOf("") }
    val filteredFriends = friends.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selecciona amigos", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar por nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(filteredFriends) { friend ->
                val isSelected = selectedFriends.contains(friend.phone)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = friend.avatar),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(friend.name)
                        Text(friend.phone, style = MaterialTheme.typography.bodySmall)
                    }
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = {
                            if (isSelected) {
                                selectedFriends.remove(friend.phone)
                            } else {
                                selectedFriends.add(friend.phone)
                            }
                        }
                    )
                }
            }
        }

        Button(onClick = {
            val amigosSeleccionados = friends.filter { selectedFriends.contains(it.phone) }
            crearEventoViewModel.updateAmigosInvitados(amigosSeleccionados)
            Toast.makeText(context, "Amigos guardados", Toast.LENGTH_SHORT).show()
            scope.launch {
                delay(800)
                navController.popBackStack()
            }
        }) {
            Text("Confirmar selección")
        }
    }
}
