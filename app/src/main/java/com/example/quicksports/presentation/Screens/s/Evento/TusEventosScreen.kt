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
import com.example.quicksports.presentation.Screens.BottomNavigationBar
import java.time.format.DateTimeFormatter
import com.example.quicksports.R

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TusEventosScreen(viewModel: TusEventosViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    val eventos by viewModel.eventos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var eventoAEliminarIndex by remember { mutableStateOf(-1) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tus eventos",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF4B5320), Color.Black)
                    )
                )
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (eventos.isEmpty()) {
                Text(
                    "Aún no has creado ningún evento.",
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
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
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular))
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
                                    "Fecha y hora: ${evento.fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}",
                                    color = Color.LightGray
                                )
                                Text("Máx. participantes: ${evento.maxParticipantes}", color = Color.LightGray)

                                Spacer(modifier = Modifier.height(8.dp))

                                if (evento.amigosInvitados.isNotEmpty()) {
                                    Text(
                                        "Amigos invitados:",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                                    )
                                    evento.amigosInvitados.forEach {
                                        Text("- ${it.name} (${it.phone})", color = Color.White)
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
                Text("Eliminar evento", fontFamily = FontFamily(Font(R.font.poppins_regular)))
            },
            text = {
                Text("¿Estás seguro de que quieres eliminar este evento?", fontFamily = FontFamily(Font(R.font.poppins_regular)))
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
                    Text("Cancelar", fontFamily = FontFamily(Font(R.font.poppins_regular)))
                }
            },
            containerColor = Color(0xFF1C1C1C),
            titleContentColor = Color.White,
            textContentColor = Color.White
        )
    }
}
