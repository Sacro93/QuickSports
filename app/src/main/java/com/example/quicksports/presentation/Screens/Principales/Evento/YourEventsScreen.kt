package com.example.quicksports.presentation.Screens.Principales.Evento


import android.app.Application
import com.example.quicksports.presentation.ViewModel.Eventos.YourEventsViewModel
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.time.format.DateTimeFormatter
import com.example.quicksports.presentation.ViewModel.Eventos.YourEventsViewModelFactory
import com.example.quicksports.presentation.components.EventConfirmationStatus

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourEventsScreen(
    navController: NavController,
    viewModel: YourEventsViewModel = viewModel(
        factory = YourEventsViewModelFactory(LocalContext.current.applicationContext as Application)
    )
){
    val context = LocalContext.current
    val event by viewModel.events.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var eventToDelete by remember { mutableIntStateOf(-1) }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF355C7D),
            Color(0xFF2A4D65),
            Color(0xFF1F3B4D),
            Color(0xFF152C3A)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tus eventos", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.background(backgroundGradient)
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (event.isEmpty()) {
                Text(
                    "Aún no has creado ningún evento.",
                    color = Color.White,
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    itemsIndexed(event) { index, event ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "Deporte: ${event.sport.name}",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White,
                                    )
                                    IconButton(onClick = {
                                        eventToDelete = index
                                        showDialog = true
                                    }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Eliminar evento",
                                            tint = Color.White
                                        )
                                    }
                                }
                                Text("Centro: ${event.center.name}", color = Color.LightGray)
                                Text("Dirección: ${event.center.address}", color = Color.LightGray)
                                Text("Teléfono centro: ${event.center.contactPhone}", color = Color.LightGray)
                                Text(
                                    text = "Fecha y hora: ${event.dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}",
                                    color = Color.LightGray
                                )
                                Text("Máx. participantes: ${event.maxParticipants}", color = Color.LightGray)

                                        Spacer(modifier = Modifier.height(8.dp))

                                        if (event.friendInvited.isNotEmpty()) {
                                            Text(
                                                "Amigos invitados:",
                                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                                color = Color.White,
                                            )
                                            event.friendInvited.forEach {
                                                Text("- ${it.name} (Contacto: +34 ${it.phone})", color = Color.White)
                                            }
                                        } else {
                                            Text("Sin amigos invitados.", color = Color.Gray)
                                        }
                                    }
                            Spacer(modifier = Modifier.height(8.dp))

                            EventConfirmationStatus(
                                creationTime = event.creationTime,
                                centerPhone = event.center.contactPhone
                            )
                            }


                        }
                    }
                }
            }
        }

    if (showDialog && eventToDelete != -1) {
        ModalBottomSheet(
            onDismissRequest = { showDialog = false },
            containerColor = Color(0xFF1C1C1C),
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¿Eliminar evento?",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Esta acción eliminará el evento de forma permanente.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            showDialog = false
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                    ) {
                        Text("Cancelar", color = Color.White)
                    }

                    Button(
                        onClick = {
                            viewModel.deleteEvent(eventToDelete)
                            Toast.makeText(context, "Evento eliminado", Toast.LENGTH_SHORT).show()
                            showDialog = false
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64FFDA))
                    ) {
                        Text("Eliminar", color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}
