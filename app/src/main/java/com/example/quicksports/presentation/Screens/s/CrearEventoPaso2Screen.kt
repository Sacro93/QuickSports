package com.example.quicksports.presentation.Screens.s

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.presentation.ViewModel.CrearEventoViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.quicksports.data.repository.EventoRepository
import com.example.quicksports.presentation.ViewModel.FriendsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CrearEventoPaso2Screen(
    navController: NavController,
    viewModel: CrearEventoViewModel = viewModel(),
    friendsViewModel: FriendsViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Repositorio para guardar eventos
    val eventoRepository = remember { EventoRepository(context) }

    val fechaHora by viewModel.fechaHora.collectAsState()
    val maxParticipantes by viewModel.maxParticipantes.collectAsState()
    val amigosInvitados by viewModel.amigosInvitados.collectAsState()

    var localDateTime by remember { mutableStateOf(fechaHora ?: LocalDateTime.now()) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var cantidad by remember { mutableStateOf(maxParticipantes.takeIf { it > 0 } ?: 0) }

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    val dateText = remember(localDateTime) { localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) }
    val timeText = remember(localDateTime) { localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Selecciona fecha y hora", style = MaterialTheme.typography.headlineSmall)

            // Fecha
            Card(
                modifier = Modifier.clickable {
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val newDate = LocalDate.of(year, month + 1, day)
                            val currentTime = localDateTime.toLocalTime()
                            localDateTime = LocalDateTime.of(newDate, currentTime)
                            viewModel.updateFechaHora(localDateTime)
                        },
                        localDateTime.year,
                        localDateTime.monthValue - 1,
                        localDateTime.dayOfMonth
                    ).show()
                },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    text = "📅 $dateText",
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Hora
            Card(
                modifier = Modifier.clickable {
                    TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            val currentDate = localDateTime.toLocalDate()
                            val newTime = LocalTime.of(hour, minute)
                            localDateTime = LocalDateTime.of(currentDate, newTime)
                            viewModel.updateFechaHora(localDateTime)
                        },
                        localDateTime.hour,
                        localDateTime.minute,
                        true
                    ).show()
                },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    text = "⏰ $timeText",
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Participantes
            Text("Máximo de participantes", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    if (cantidad > 0) {
                        cantidad--
                        viewModel.updateMaxParticipantes(cantidad)
                    }
                }) {
                    Text("-")
                }
                Text(text = "$cantidad", modifier = Modifier.padding(horizontal = 16.dp))
                Button(onClick = {
                    cantidad++
                    viewModel.updateMaxParticipantes(cantidad)
                }) {
                    Text("+")
                }
            }

            // Botón agregar amigos
            Button(onClick = {
                friendsViewModel.loadFriends()
                scope.launch {
                    delay(150)
                    navController.navigate("friend_selector")
                }
            }) {
                Text("Agregar amigos")
            }

            if (amigosInvitados.isNotEmpty()) {
                Text("Amigos seleccionados:", style = MaterialTheme.typography.titleMedium)
                amigosInvitados.forEach { amigo ->
                    Text("• ${amigo.name} (${amigo.phone})")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Confirmar evento
            Button(onClick = {
                showConfirmDialog = true
            }) {
                Text("Confirmar evento")
            }
        }
    }

    // Diálogo de confirmación
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("¿Estás seguro de confirmar?") },
            confirmButton = {
                TextButton(onClick = {
                    val evento = viewModel.armarEvento()
                    if (evento != null) {
                        scope.launch {
                            eventoRepository.guardarEvento(evento) // <-- 🔥 PERSISTENCIA REAL
                            Toast.makeText(context, "Evento guardado correctamente", Toast.LENGTH_SHORT).show()
                            showConfirmDialog = false
                            navController.navigate("home") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Faltan completar datos del evento", Toast.LENGTH_SHORT).show()
                        showConfirmDialog = false
                    }
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                }) {
                    Text("Volver")
                }
                TextButton(onClick = {
                    navController.navigate("home") {
                        popUpTo(0) { inclusive = true }
                    }
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

