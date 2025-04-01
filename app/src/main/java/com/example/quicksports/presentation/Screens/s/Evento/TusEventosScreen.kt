package com.example.quicksports.presentation.Screens.s.Evento


import com.example.quicksports.presentation.ViewModel.TusEventosViewModel
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.time.format.DateTimeFormatter
import com.example.quicksports.R

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TusEventosScreen(viewModel: TusEventosViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    val eventos by viewModel.eventos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var eventoAEliminarIndex by remember { mutableIntStateOf(-1) }

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
            if (eventos.isEmpty()) {
                Text(
                    "Aún no has creado ningún evento.",
                    color = Color.White,
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    itemsIndexed(eventos) { index, evento ->
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
                                        "Deporte: ${evento.deporte.name}",
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White,
                                    )
                                    IconButton(onClick = {
                                        eventoAEliminarIndex = index
                                        showDialog = true
                                    }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Eliminar evento",
                                            tint = Color.White
                                        )
                                    }
                                }
                                Text("Centro: ${evento.centro.name}", color = Color.LightGray)
                                Text("Dirección: ${evento.centro.address}", color = Color.LightGray)
                                Text("Teléfono centro: ${evento.centro.contactPhone}", color = Color.LightGray)
                                Text(
                                    text = "Fecha y hora: ${evento.fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}",
                                    color = Color.LightGray
                                )
                                            Text("Máx. participantes: ${evento.maxParticipantes}", color = Color.LightGray)

                                        Spacer(modifier = Modifier.height(8.dp))

                                        if (evento.amigosInvitados.isNotEmpty()) {
                                            Text(
                                                "Amigos invitados:",
                                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                                color = Color.White,
                                            )
                                            evento.amigosInvitados.forEach {
                                                Text("- ${it.name} (Contacto: +34 ${it.phone})", color = Color.White)
                                            }
                                        } else {
                                            Text("Sin amigos invitados.", color = Color.Gray)
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }

        if (showDialog && eventoAEliminarIndex != -1) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text("Eliminar evento")
                },
                text = {
                    Text("¿Estás seguro de que quieres eliminar este evento?")
                },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.eliminarEvento(eventoAEliminarIndex)
                        Toast.makeText(context, "Evento eliminado", Toast.LENGTH_SHORT).show()
                        showDialog = false
                    }) {
                        Text("Sí", fontFamily = FontFamily(Font(R.font.poppins_regular)))
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                    }) {
                        Text("Cancelar")                    }
                },
                containerColor = Color(0xFF1C1C1C),
                titleContentColor = Color.White,
                textContentColor = Color.White
            )
        }
    }
