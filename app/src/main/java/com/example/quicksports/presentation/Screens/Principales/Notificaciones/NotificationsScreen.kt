package com.example.quicksports.presentation.Screens.Principales.Notificaciones

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController) {
    val notifications = listOf(
        "🎉 Jorge aceptó tu solicitud de amistad",
        "🎾 Pedro te ha invitado a un evento de pádel",
        "💬 Valentina Ruiz aceptó tu solicitud de amistad",
        "🕐 Se actualizó el horario de tu evento",
        "📅 El centro deportivo Barcelona Padel confirmó tu reserva para el 05/05/2025 a las 20:00",
        "📅 El centro deportivo Zona Norte Fútbol aceptó tu evento para el 08/05/2025 a las 18:30"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Notificaciones",
                        style = MaterialTheme.typography.titleLarge.copy(
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
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF7F00FF),
                            Color(0xFF3F51B5),
                            Color(0xFF3F51B5),
                            Color(0xFF162F3C),
                            Color(0xFF0F1D20))
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                notifications.forEach { mensaje ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.08f))
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(32.dp)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color(0xFF80D0C7), Color(0xFF0093E9))
                                    ),
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = mensaje,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
