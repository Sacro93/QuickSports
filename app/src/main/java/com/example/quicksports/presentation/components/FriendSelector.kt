package com.example.quicksports.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.quicksports.presentation.ViewModel.FriendsViewModel
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.presentation.ViewModel.CrearEventoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendSelectorScreen(
    navController: NavController,
    viewModel: CrearEventoViewModel = viewModel(),
    friendsViewModel: FriendsViewModel = viewModel()
) {
    val context = LocalContext.current
    val friends by friendsViewModel.friends.collectAsState()
    val selectedFriends by friendsViewModel.selectedFriends.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }

    if (showMessage) {
        Toast.makeText(
            context,
            "Solicitud de participación enviada. Resta su confirmación...",
            Toast.LENGTH_LONG
        ).show()

        LaunchedEffect(Unit) {
            Handler(Looper.getMainLooper()).postDelayed({
                navController.popBackStack()
            }, 4000)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Selecciona amigos") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar amigo") },
                modifier = Modifier.fillMaxWidth()
            )

            val filteredFriends = friends.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }

            Column(modifier = Modifier.weight(1f)) {
                filteredFriends.forEach { friend ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = friend.avatar),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = friend.name, color = Color.White)
                            Text(text = friend.phone, color = Color.Gray)
                        }
                        Checkbox(
                            checked = selectedFriends.contains(friend),
                            onCheckedChange = { friendsViewModel.toggleFriendSelection(friend) }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.updateAmigosInvitados(selectedFriends.toList())
                    showMessage = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar selección")
            }
        }
    }
}
