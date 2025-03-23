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
import com.example.quicksports.Screen
import com.example.quicksports.data.repository.EventoRepository
import com.example.quicksports.presentation.ViewModel.FriendsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CrearEventoPaso2Screen(
    navController: NavController,
    crearEventoViewModel: CrearEventoViewModel,
    friendsViewModel: FriendsViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val eventoRepository = remember { EventoRepository(context) }

    val selectedSport by crearEventoViewModel.selectedSport.collectAsState()
    val selectedCenter by crearEventoViewModel.selectedCenter.collectAsState()
    val fechaHora by crearEventoViewModel.fechaHora.collectAsState()
    val maxParticipantes by crearEventoViewModel.maxParticipantes.collectAsState()
    val amigosInvitados by crearEventoViewModel.amigosInvitados.collectAsState()

    var localDateTime by remember { mutableStateOf(fechaHora ?: LocalDateTime.now()) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var cantidad by remember { mutableStateOf(maxParticipantes.takeIf { it > 0 } ?: 0) }

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
            Text("Confirmación de evento", style = MaterialTheme.typography.headlineSmall)

            selectedSport?.let {
                Text("Deporte seleccionado: ${it.name}", style = MaterialTheme.typography.titleMedium)
            }

            selectedCenter?.let {
                Text("Centro: ${it.name}", style = MaterialTheme.typography.titleMedium)
            }

            if (amigosInvitados.isNotEmpty()) {
                Text("Amigos invitados:", style = MaterialTheme.typography.titleMedium)
                amigosInvitados.forEach { amigo ->
                    Text("• ${amigo.name} (${amigo.phone})")
                }
            } else {
                Text("No se han seleccionado amigos aún.", style = MaterialTheme.typography.bodyMedium)
            }

            Text("Selecciona fecha y hora", style = MaterialTheme.typography.titleMedium)

            Card(
                modifier = Modifier.clickable {
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val newDate = LocalDate.of(year, month + 1, day)
                            val currentTime = localDateTime.toLocalTime()
                            localDateTime = LocalDateTime.of(newDate, currentTime)
                            crearEventoViewModel.updateFechaHora(localDateTime)
                        },
                        localDateTime.year,
                        localDateTime.monthValue - 1,
                        localDateTime.dayOfMonth
                    ).show()
                },
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text("📅 $dateText", modifier = Modifier.padding(16.dp))
            }

            Card(
                modifier = Modifier.clickable {
                    TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            val currentDate = localDateTime.toLocalDate()
                            val newTime = LocalTime.of(hour, minute)
                            localDateTime = LocalDateTime.of(currentDate, newTime)
                            crearEventoViewModel.updateFechaHora(localDateTime)
                        },
                        localDateTime.hour,
                        localDateTime.minute,
                        true
                    ).show()
                },
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text("⏰ $timeText", modifier = Modifier.padding(16.dp))
            }

            Text("Máximo de participantes", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    if (cantidad > 0) {
                        cantidad--
                        crearEventoViewModel.updateMaxParticipantes(cantidad)
                    }
                }) {
                    Text("-")
                }
                Text(text = "$cantidad", modifier = Modifier.padding(horizontal = 16.dp))
                Button(onClick = {
                    cantidad++
                    crearEventoViewModel.updateMaxParticipantes(cantidad)
                }) {
                    Text("+")
                }
            }

            Button(onClick = {
                scope.launch {
                    friendsViewModel.loadFriends()
                    delay(100)
                    navController.navigate(Screen.FriendSelector.route)
                }
            }) {
                Text("Agregar amigos")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                showConfirmDialog = true
            }) {
                Text("Confirmar evento")
            }
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("¿Estás seguro de confirmar?") },
            confirmButton = {
                TextButton(onClick = {
                    val evento = crearEventoViewModel.armarEvento()
                    if (evento != null) {
                        scope.launch {
                            eventoRepository.guardarEvento(evento)
                            Toast.makeText(context, "Evento guardado correctamente", Toast.LENGTH_SHORT).show()
                            showConfirmDialog = false
                            navController.navigate(Screen.Home.route) {
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
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Volver")
                }
                TextButton(onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
