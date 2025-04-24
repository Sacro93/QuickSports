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
import androidx.navigation.NavController
import com.example.quicksports.presentation.ViewModel.Eventos.CreateEventViewModel
import com.example.quicksports.presentation.ViewModel.Friends.FriendsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.quicksports.data.preferences.SafeAvatarImage
import com.example.quicksports.presentation.ViewModel.Friends.FriendsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FriendSelectorScreen(
    navController: NavController,
    createEventViewModel: CreateEventViewModel,
    friendsViewModel: FriendsViewModel = viewModel(
        factory = FriendsViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val friends by friendsViewModel.friends.collectAsState()
    val amigosInvitados by createEventViewModel.invitedFriends.collectAsState()

    val selectedFriends = remember { mutableStateListOf<String>() }

    LaunchedEffect(amigosInvitados) {
        selectedFriends.clear()
        selectedFriends.addAll(amigosInvitados.map { it.phone })
    }

    var searchQuery by remember { mutableStateOf("") }
    val filteredFriends = friends.filter { it.name.contains(searchQuery, ignoreCase = true) }

    val hasSelection = selectedFriends.isNotEmpty()

    Box(
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
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Selecciona amigos",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Buscar por nombre") },
                        modifier = Modifier.fillMaxWidth(),
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

                    if (friends.isNotEmpty()) {
                        Text(
                            text = "Amigos seleccionados: ${selectedFriends.size}",
                            color = Color.White.copy(alpha = 0.7f),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredFriends) { friend ->
                            val isSelected = selectedFriends.contains(friend.phone)
                            val sportsNames = friend.favoriteSports.joinToString(", ")

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

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = "Tel: ${friend.phone}",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = Color.White.copy(alpha = 0.7f)
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(2.dp))

                                        Text(
                                            text = "Deportes favoritos: $sportsNames",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = Color.White.copy(alpha = 0.7f)
                                            )
                                        )
                                    }
                                }

                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = {
                                        if (isSelected) {
                                            selectedFriends.remove(friend.phone)
                                        } else {
                                            selectedFriends.add(friend.phone)
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color.White,
                                        uncheckedColor = Color.White,
                                        checkmarkColor = Color.Black
                                    )
                                )
                            }
                        }

                        item { Spacer(modifier = Modifier.height(90.dp)) }
                    }
                }
            }
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    val friendsSelected = friends.filter { selectedFriends.contains(it.phone) }
                    createEventViewModel.updateInvitedFriends(friendsSelected)
                    Toast.makeText(context, "Amigos actualizados", Toast.LENGTH_SHORT).show()
                    scope.launch {
                        delay(800)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (hasSelection) Color(0xFF64FFDA) else Color.Gray,
                    contentColor = if (hasSelection) Color.Black else Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                enabled = true
            ) {
                Text("Confirmar selección")
            }
        }
    }
}
