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
    friendsViewModel: FriendsViewModel = viewModel(),
    crearEventoViewModel: CrearEventoViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Carga de amigos desde persistencia si aún no están cargados
    val friends by friendsViewModel.friends.collectAsState()
    val amigosInvitados by crearEventoViewModel.amigosInvitados.collectAsState()

    // Lista mutable de selección actual
    val selectedFriends = remember { mutableStateListOf<Friend>() }

    // Al entrar a la pantalla, copiar los que ya estaban seleccionados
    LaunchedEffect(amigosInvitados) {
        selectedFriends.clear()
        selectedFriends.addAll(amigosInvitados)
    }

    // Búsqueda
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
                        checked = selectedFriends.contains(friend),
                        onCheckedChange = {
                            if (selectedFriends.contains(friend)) {
                                selectedFriends.remove(friend)
                            } else {
                                selectedFriends.add(friend)
                            }
                        }
                    )
                }
            }
        }

        Button(onClick = {
            crearEventoViewModel.updateAmigosInvitados(selectedFriends)
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
