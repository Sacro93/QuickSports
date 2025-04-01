package com.example.quicksports.presentation.Screens.s.Evento

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.presentation.ViewModel.CrearEventoViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.example.quicksports.Screen
import com.example.quicksports.data.repository.EventoRepository
import com.example.quicksports.presentation.ViewModel.FriendsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.quicksports.R

@OptIn(ExperimentalMaterial3Api::class)
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
    var cantidad by remember { mutableIntStateOf(maxParticipantes.takeIf { it > 0 } ?: 0) }

    val dateText = remember(localDateTime) { localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) }
    val timeText = remember(localDateTime) { localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) }

    val isFormValid = selectedSport != null && selectedCenter != null && fechaHora != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    Brush.verticalGradient(
                        colors = listOf( Color(0xFF355C7D),
                            Color(0xFF2A4D65),
                            Color(0xFF1F3B4D),
                            Color(0xFF152C3A))
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = "Confirmación de evento",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    selectedSport?.let {
                        Text("Deporte: ${it.name}", color = Color.White)
                    }
                    selectedCenter?.let {
                        Text("Centro: ${it.name}", color = Color.White)
                    }
                    if (amigosInvitados.isNotEmpty()) {
                        Text("Amigos invitados:", color = Color.White, fontWeight = FontWeight.Bold)
                        amigosInvitados.forEach { amigo ->
                            Text("• ${amigo.name}", color = Color.White)
                        }
                    } else {
                        Text("No se han seleccionado amigos.", color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Selecciona fecha y hora", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                        .clickable {
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
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))
                ) {
                    Text("📅 $dateText", modifier = Modifier.padding(16.dp), color = Color.White)
                }
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                        .clickable {
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
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))
                ) {
                    Text("⏰ $timeText", modifier = Modifier.padding(16.dp), color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Participantes", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Card(
                modifier = Modifier.width(160.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        if (cantidad > 0) {
                            cantidad--
                            crearEventoViewModel.updateMaxParticipantes(cantidad)
                        }
                    }) {
                        Icon(Icons.Default.Remove, contentDescription = null, tint = Color.White)
                    }
                    Text("$cantidad", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))
                    IconButton(onClick = {
                        cantidad++
                        crearEventoViewModel.updateMaxParticipantes(cantidad)
                    }) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            friendsViewModel.loadFriends()
                            delay(100)
                            navController.navigate(Screen.FriendSelector.route)
                        }
                    },
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar amigos", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { Toast.makeText(context, "Funcionalidad a futuro", Toast.LENGTH_SHORT).show() },
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.CreditCard, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Método de pago", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(180.dp))
            Button(
                onClick = { showConfirmDialog = true },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(48.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = if (isFormValid) Color(0xFFCCCCCC) else Color.Gray),
                shape = RoundedCornerShape(10.dp),
                enabled = isFormValid
            ) {
                Text("Confirmar evento", color = Color.Black)
            }


        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = {
                Text(
                    text = "¿Estás seguro de confirmar?",
                    fontWeight = FontWeight.SemiBold
                )
            },
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
                    Text("Sí", fontFamily = FontFamily(Font(R.font.poppins_regular)))
                }
            },
            dismissButton = {
                Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                    TextButton(onClick = { showConfirmDialog = false }) {
                        Text("Volver")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Text("Cancelar")
                    }
                }
            },
            containerColor = Color(0xFF1C1C1C),
            titleContentColor = Color.White,
            textContentColor = Color.White
        )
    }
}
