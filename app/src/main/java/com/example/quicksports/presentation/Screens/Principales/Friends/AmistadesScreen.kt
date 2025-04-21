package com.example.quicksports.presentation.Screens.Principales.Friends

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quicksports.presentation.ViewModel.Friends.FriendsViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quicksports.data.preferences.SafeAvatarImage
import com.example.quicksports.data.models.Friend
import com.example.quicksports.presentation.Screens.BottomNavigationBar
import com.example.quicksports.presentation.Screens.ResetFriendsButton
import com.example.quicksports.presentation.ViewModel.Friends.FriendsViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AmistadesScreen(
    navController: NavController,
    friendsViewModel: FriendsViewModel = viewModel(
        factory = FriendsViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val allFriends by friendsViewModel.friends.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    val filteredFriends = allFriends.filter { it.name.contains(searchQuery, ignoreCase = true) }

    var friendToDelete by remember { mutableStateOf<Friend?>(null) }
    val addedFriends = remember { mutableStateListOf<String>() }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, containerColor = Color(0xAA0F1D20))
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2F6A76),
                            Color(0xFF1B3B45),
                            Color(0xFF0F1D20)
                        )
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Amistades",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por nombre o apellido") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.White.copy(alpha = 0.05f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.03f),
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(filteredFriends) { friend ->
                    val isAdded = addedFriends.contains(friend.phone)
                    val canSendRequest = friend.name.startsWith("Valentina") || friend.name.startsWith("Tamara")

                    val deportesText = friend.deportesFavoritos.joinToString(", ")

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SafeAvatarImage(friend.avatar)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = friend.name,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium
                                    )
                                )

                                Spacer(modifier = Modifier.height(4.dp)) // 👈 Añade espacio entre nombre y teléfono

                                Text(
                                    text = "Tel: ${friend.phone}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                )

                                Spacer(modifier = Modifier.height(2.dp)) // 👈 Opcional, pequeño espacio extra

                                Text(
                                    text = "Deportes favoritos: $deportesText",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                )
                            }
                        }

                        Row {
                            if (canSendRequest) {
                                IconButton(onClick = {
                                    addedFriends.add(friend.phone)
                                    Toast.makeText(
                                        context,
                                        "Solicitud enviada a ${friend.name}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }) {
                                    Icon(
                                        imageVector = if (isAdded) Icons.Default.Check else Icons.Default.PersonAdd,
                                        contentDescription = null,
                                        tint = if (isAdded) Color(0xFF81C784) else Color.White
                                    )
                                }
                            } else {
                                IconButton(onClick = { friendToDelete = friend }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar amigo",
                                        tint = Color.White.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        friendToDelete?.let { friend ->
            AlertDialog(
                onDismissRequest = { friendToDelete = null },
                confirmButton = {
                    TextButton(onClick = {
                        scope.launch {
                            friendsViewModel.eliminarAmigo(friend.phone)
                            friendsViewModel.loadFriends()
                            Toast.makeText(
                                context,
                                "${friend.name} eliminado",
                                Toast.LENGTH_SHORT
                            ).show()
                            friendToDelete = null
                        }
                    }) {
                        Text("Eliminar", color = Color(0xFFEF9A9A))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { friendToDelete = null }) {
                        Text("Cancelar", color = Color.White)
                    }
                },
                title = {
                    Text("Confirmación", color = Color.White, fontWeight = FontWeight.Bold)
                },
                text = {
                    Text(
                        "¿Estás seguro de que deseas eliminar a ${friend.name}?",
                        color = Color.White.copy(alpha = 0.8f)
                    )
                },
                containerColor = Color(0xFF1B3B45),
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
   ResetFriendsButton(friendsViewModel = friendsViewModel)

}
