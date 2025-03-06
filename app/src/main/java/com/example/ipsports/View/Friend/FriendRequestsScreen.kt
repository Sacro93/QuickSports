package com.example.ipsports.View.Friend


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ipsports.ViewModel.ui.FriendViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendRequestsScreen(
    navController: NavController,
    viewModel: FriendViewModel = hiltViewModel()
) {
    val friendRequests by viewModel.friendRequests.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Solicitudes de Amistad", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF15272D))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Solicitudes pendientes:", color = Color.White, style = MaterialTheme.typography.titleLarge)

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else if (friendRequests.isEmpty()) {
                    Text("No tienes solicitudes pendientes.", color = Color.White)
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(friendRequests.toList()) { request ->
                            FriendRequestItem(
                                request = request,
                                onAccept = { viewModel.acceptRequest(request.senderId) },
                                onReject = { viewModel.rejectRequest(request.senderId) }
                            )
                        }
                    }

                }

                errorMessage?.let {
                    Text(it, color = Color.Red)
                }
            }
        }
    }
}

