package com.example.quicksports.presentation.Screens.s

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CrearEventoPaso2Screen(navController: NavController, viewModel: CrearEventoViewModel = viewModel()) {
    val context = LocalContext.current
    val fechaHora by viewModel.fechaHora.collectAsState()
    val maxParticipantes by viewModel.maxParticipantes.collectAsState()
    val amigosInvitados by viewModel.amigosInvitados.collectAsState()

    var localDateTime by remember { mutableStateOf(fechaHora ?: LocalDateTime.now()) }
    var participantesInput by remember { mutableStateOf(maxParticipantes.takeIf { it > 0 }?.toString() ?: "") }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

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

            val dateText = remember(localDateTime) { localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) }
            val timeText = remember(localDateTime) { localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) }

            Text(
                text = dateText,
                modifier = Modifier
                    .clickable {
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
                    }
                    .padding(12.dp)
            )

            Text(
                text = timeText,
                modifier = Modifier
                    .clickable {
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
                    }
                    .padding(12.dp)
            )

            OutlinedTextField(
                value = participantesInput,
                onValueChange = {
                    participantesInput = it
                    viewModel.updateMaxParticipantes(it.toIntOrNull() ?: 0)
                },
                label = { Text("Máximo de participantes") },
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = {
                navController.navigate("friend_selector")
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
                    val evento = viewModel.armarEvento()
                    if (evento != null) {
                        Toast.makeText(context, "Evento guardado", Toast.LENGTH_SHORT).show()
                        showConfirmDialog = false
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
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
