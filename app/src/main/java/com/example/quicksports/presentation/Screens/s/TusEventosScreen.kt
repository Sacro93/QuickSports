package com.example.quicksports.presentation.Screens.s


import com.example.quicksports.presentation.ViewModel.TusEventosViewModel
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TusEventosScreen(viewModel: TusEventosViewModel = viewModel()) {
    val context = LocalContext.current
    val eventos by viewModel.eventos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var eventoAEliminarIndex by remember { mutableStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Tus eventos") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (eventos.isEmpty()) {
                Text("Aún no has creado ningún evento.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    itemsIndexed(eventos) { index, evento ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "Deporte: ${evento.deporte.name}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    IconButton(onClick = {
                                        eventoAEliminarIndex = index
                                        showDialog = true
                                    }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Eliminar evento"
                                        )
                                    }
                                }
                                Text("Centro: ${evento.centro.name}")
                                Text("Dirección: ${evento.centro.address}")
                                Text("Teléfono centro: ${evento.centro.contactPhone}")
                                Text(
                                    "Fecha y hora: ${
                                        evento.fechaHora.format(
                                            DateTimeFormatter.ofPattern(
                                                "dd/MM/yyyy HH:mm"
                                            )
                                        )
                                    }"
                                )
                                Text("Máx. participantes: ${evento.maxParticipantes}")

                                Spacer(modifier = Modifier.height(8.dp))

                                if (evento.amigosInvitados.isNotEmpty()) {
                                    Text(
                                        "Amigos invitados:",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    evento.amigosInvitados.forEach {
                                        Text("- ${it.name} (${it.phone})")
                                    }
                                } else {
                                    Text("Sin amigos invitados.")
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
            title = { Text("Eliminar evento") },
            text = { Text("¿Estás seguro de que quieres eliminar este evento?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.eliminarEvento(eventoAEliminarIndex)
                    Toast.makeText(context, "Evento eliminado", Toast.LENGTH_SHORT).show()
                    showDialog = false
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
