package com.example.quicksports.presentation.View.Screens.Evento

import android.app.Application
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.presentation.ViewModel.Eventos.CreateEventViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.example.quicksports.presentation.Navigation.Screen
import com.example.quicksports.data.repository.EventRepository
import com.example.quicksports.presentation.ViewModel.Eventos.CreateEventViewModelFactory
import com.example.quicksports.presentation.ViewModel.Friends.FriendsViewModel
import com.example.quicksports.presentation.ViewModel.Friends.FriendsViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.quicksports.presentation.View.components.CuentaRegresivaConCancelacion


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
    fun CreateEventScreen2(
    navController: NavController,
     createEventViewModel: CreateEventViewModel = viewModel(
        factory = CreateEventViewModelFactory()
    ),
    friendsViewModel: FriendsViewModel = viewModel(
        factory = FriendsViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val eventRepository = remember { EventRepository(context) }

    val selectedSport by createEventViewModel.selectedSport.collectAsState()
    val selectedCenter by createEventViewModel.selectedCenter.collectAsState()
    val dateTime by createEventViewModel.dateTime.collectAsState()
    val maxParticipants by createEventViewModel.maxParticipants.collectAsState()
    val invitedFriends by createEventViewModel.invitedFriends.collectAsState()

    var localDateTime by remember { mutableStateOf(dateTime ?: LocalDateTime.now()) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var quantity by remember { mutableIntStateOf(maxParticipants.takeIf { it > 0 } ?: 0) }

    val dateText = remember(localDateTime) { localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) }
    val timeText = remember(localDateTime) { localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) }

    val isFormValid = selectedSport != null && selectedCenter != null && dateTime != null

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
                        colors = listOf(
                            Color(0xFF355C7D),
                            Color(0xFF2A4D65),
                            Color(0xFF1F3B4D),
                            Color(0xFF152C3A)
                        )
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Confirmación de evento",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    selectedSport?.let {
                        Text("Deporte: ${it.name}", color = Color.White)
                    }
                    selectedCenter?.let {
                        Text("Centro: ${it.name}", color = Color.White)
                    }
                    if (invitedFriends.isNotEmpty()) {
                        Text("Amigos invitados:", color = Color.White, fontWeight = FontWeight.Bold)
                        invitedFriends.forEach { amigo ->
                            Text("• ${amigo.name}", color = Color.White)
                        }
                    } else {
                        Text("No se han seleccionado amigos.", color = Color.Gray)
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Selecciona fecha y hora", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Card(
                        modifier = Modifier.weight(1f).clickable {
                            DatePickerDialog(
                                context,
                                { _, year, month, day ->
                                    val newDate = LocalDate.of(year, month + 1, day)
                                    val currentTime = localDateTime.toLocalTime()
                                    localDateTime = LocalDateTime.of(newDate, currentTime)
                                    createEventViewModel.updateDate(localDateTime)
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
                        modifier = Modifier.weight(1f).clickable {
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    val currentDate = localDateTime.toLocalDate()
                                    val newTime = LocalTime.of(hour, minute)
                                    localDateTime = LocalDateTime.of(currentDate, newTime)
                                    createEventViewModel.updateDate(localDateTime)
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
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Participantes", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Card(
                    modifier = Modifier.width(160.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            if (quantity > 0) {
                                quantity--
                                createEventViewModel.updateMaxParticipants(quantity)
                            }
                        }) {
                            Icon(Icons.Default.Remove, contentDescription = null, tint = Color.White)
                        }
                        Text("$quantity", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))
                        IconButton(onClick = {
                            quantity++
                            createEventViewModel.updateMaxParticipants(quantity)
                        }) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth().clickable {
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

            Card(
                modifier = Modifier.fillMaxWidth().clickable {
                    Toast.makeText(context, "Próximamente", Toast.LENGTH_SHORT).show()
                },
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

            CuentaRegresivaConCancelacion(
                centerPhone = selectedCenter?.contactPhone ?: "el centro",
                onTimeout = {
                    createEventViewModel.reset()
                    Toast.makeText(context, "Tiempo expirado. Evento cancelado.", Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { showConfirmDialog = true },
                modifier = Modifier.fillMaxWidth(0.9f).height(48.dp).align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = if (isFormValid) Color(0xFFCCCCCC) else Color.Gray),
                shape = RoundedCornerShape(10.dp),
                enabled = isFormValid
            ) {
                Text("Confirmar evento", color = Color.Black)
            }
        }
    }

    if (showConfirmDialog) {
        ModalBottomSheet(
            onDismissRequest = { showConfirmDialog = false },
            containerColor = Color(0xFF1C1C1C),
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¿Confirmar evento?",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Una vez confirmado, se notificará a tus amigos invitados.",
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
                            showConfirmDialog = false
                            navController.navigate(Screen.Home.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                    ) {
                        Text("Cancelar", color = Color.White)
                    }

                    Button(
                        onClick = {
                            val event = createEventViewModel.settingUpAnEvent()
                            if (event != null) {
                                scope.launch {
                                    eventRepository.saveEvent(event)
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
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64FFDA))
                    ) {
                        Text("Confirmar", color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
