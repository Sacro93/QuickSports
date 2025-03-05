package com.example.ipsports.View.Event.pg2

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ipsports.View.Event.ReusableEvent.EventCreationProgressBar
import com.example.ipsports.View.Event.ReusableEvent.EventInputField
import com.example.ipsports.View.Event.ReusableEvent.FieldType
import com.example.ipsports.View.Reusable.ButtonPrimary
import java.text.SimpleDateFormat
import java.util.Locale
import com.google.firebase.Timestamp
import android.app.DatePickerDialog
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ipsports.ViewModel.ui.EventInfoViewModel
import com.example.ipsports.data.routesNavigation.Routes


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EventInfoScreen(
    sportId: String,
    navController: NavController,
    eventInfoViewModel: EventInfoViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val uiState by eventInfoViewModel.uiState.collectAsStateWithLifecycle()
    val sportsList by eventInfoViewModel.sports.collectAsStateWithLifecycle()
    val centersWithSports by eventInfoViewModel.centersWithSports.collectAsStateWithLifecycle()

    val sportName = sportsList.find { it.id == sportId }?.name ?: "Deporte no encontrado"
    val availableCenters = centersWithSports.filter { it.sports.any { sport -> sport.id == sportId } }
        .map { it.center }

    val context = LocalContext.current

    LaunchedEffect(sportId) {
        eventInfoViewModel.loadCentersForSport(sportId)
    }

    //  Recuperar amigos invitados al regresar de `AddFriendsScreen`
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    LaunchedEffect(navBackStackEntry) {
        navBackStackEntry?.savedStateHandle?.get<List<String>>("invitedFriends")?.let { friends ->
            eventInfoViewModel.updateInvitedFriends(friends)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Evento de $sportName", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF337C8D),
                            Color(0xFF15272D),
                            Color(0xFF17272B)
                        )
                    )
                )
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                EventCreationProgressBar(currentPage = 3, totalPages = 4)

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Selecciona los detalles del evento",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        // 📅 Selección de Fecha y Hora del Evento
                        EventInputField(
                            label = "Fecha y Hora del Evento",
                            value = uiState.dateTime?.let { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(it) } ?: "",
                            onValueChange = {},
                            fieldType = FieldType.Date,
                            leadingIcon = {
                                Icon(Icons.Default.DateRange, contentDescription = "Fecha y Hora")
                            },
                            onClick = {
                                println("📅 Se presionó el selector de fecha")
                                val newCalendar = Calendar.getInstance()
                                DatePickerDialog(context, { _, year, month, day ->
                                    newCalendar.set(year, month, day)
                                    TimePickerDialog(context, { _, hour, minute ->
                                        newCalendar.set(Calendar.HOUR_OF_DAY, hour)
                                        newCalendar.set(Calendar.MINUTE, minute)
                                        eventInfoViewModel.updateDate(newCalendar.time)
                                        println(" Fecha seleccionada: ${newCalendar.time}")
                                    }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true).show()
                                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show()
                            }
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // 📍 Selección del centro deportivo
                        OptionCenters(
                            selectedCenter = uiState.selectedCenter,
                            centers = availableCenters,
                            onCenterSelected = { eventInfoViewModel.updateSelectedCenter(it) }
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // 👥 Participantes máximos
                        EventInputField(
                            label = "Participantes Máximos",
                            value = uiState.maxParticipants,
                            onValueChange = { eventInfoViewModel.updateMaxParticipants(it) },
                            fieldType = FieldType.Text,
                            leadingIcon = {
                                Icon(Icons.Default.Group, contentDescription = "Participantes")
                            }
                        )

                        if ((uiState.maxParticipants.toIntOrNull() ?: 0) in 1..10 &&
                            uiState.invitedFriends.size > uiState.maxParticipants.toInt()) {
                            Text(
                                text = "⚠️ Hay más invitados que el número máximo de participantes.",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // **Botón para agregar amigos**
                        ButtonPrimary(
                            text = "Agregar Amigos",
                            onClick = { navController.navigate(Routes.FRIENDS) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // 📌 **Mostrar nombres de amigos invitados**
                        if (uiState.invitedFriends.isNotEmpty()) {
                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                Text(
                                    text = "Invitados:",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )

                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    uiState.invitedFriends.forEach { friend ->
                                        AssistChip(
                                            onClick = {},
                                            label = { Text(friend, color = Color.White) },
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = Icons.Default.Person,
                                                    contentDescription = null,
                                                    tint = Color.White
                                                )
                                            },
                                            colors = AssistChipDefaults.assistChipColors(
                                                containerColor = Color(0xFF2E7D32)
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // **Botón Siguiente**
                    ButtonPrimary(
                        text = "Siguiente",
                        onClick = {
                            if (uiState.dateTime != null && uiState.selectedCenter != null) {
                                val timestamp = Timestamp(uiState.dateTime!!)
                                navController.navigate(
                                    "event_summary/${sportId}/${timestamp.seconds}/${uiState.selectedCenter!!.id}/${uiState.maxParticipants}/${uiState.invitedFriends.joinToString(",")}"
                                )
                            }
                        },
                        enabled = uiState.dateTime != null && uiState.selectedCenter != null
                    )
                }
            }
        }
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EventInfoScreen(
    sportId: String,
    navController: NavController,
    onContinue: (Event) -> Unit,
    onBack: () -> Unit = {}
) {

    //  ViewModel para obtener los centros deportivos
    val centerViewModel: CenterViewModel = hiltViewModel()
    val centersWithSports by centerViewModel.centersWithSports.collectAsStateWithLifecycle()

// obtener el nombre del deporte
    val sportViewModel: SportViewModel = hiltViewModel()
    val sportsList by sportViewModel.sports.collectAsStateWithLifecycle()
    val sportName = sportsList.find { it.id == sportId }?.name ?: "Deporte no encontrado"

    // fecha y hora
    var dateTime by remember { mutableStateOf<Date?>(null) }
    val context = LocalContext.current


    //  Estados de datos ingresados por el usuario
    var maxParticipants by remember { mutableStateOf("") }
    var selectedCenter by remember { mutableStateOf<Center?>(null) }
    var invitedFriends by remember { mutableStateOf<List<String>>(emptyList()) }

    //  Filtrar los centros deportivos que tienen el deporte seleccionado
    val availableCenters by remember(sportId) {
        derivedStateOf {
            centersWithSports.filter { it.sports.any { sport -> sport.id == sportId } }
                .map { it.center }
        }
    }

    // 📌 Recuperar la lista de amigos invitados cuando volvemos de `AddFriendsScreen`
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    LaunchedEffect(navBackStackEntry) {
        navBackStackEntry?.savedStateHandle?.get<List<String>>("invitedFriends")?.let { friends ->
            invitedFriends = friends
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Evento de $sportName", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF337C8D),
                            Color(0xFF15272D),
                            Color(0xFF17272B)
                        )
                    )
                )
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                EventCreationProgressBar(currentPage = 3, totalPages = 4)

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Selecciona los detalles del evento",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        // 📅 Selección de Fecha y Hora del Evento

                        EventInputField(
                            label = "Fecha y Hora del Evento",
                            value = dateTime?.let { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(it) } ?: "",
                            onValueChange = {},
                            fieldType = FieldType.Date,
                            leadingIcon = {
                                Icon(Icons.Default.DateRange, contentDescription = "Fecha y Hora")
                            },
                            onClick = {
                                val newCalendar = Calendar.getInstance()
                                DatePickerDialog(context, { _, year, month, day ->
                                    newCalendar.set(year, month, day)
                                    TimePickerDialog(context, { _, hour, minute ->
                                        newCalendar.set(Calendar.HOUR_OF_DAY, hour)
                                        newCalendar.set(Calendar.MINUTE, minute)
                                        dateTime = newCalendar.time
                                    }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true).show()
                                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show()
                            }
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        OptionCenters(
                            selectedCenter = selectedCenter,
                            centers = availableCenters,
                            onCenterSelected = { selectedCenter = it }
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        EventInputField(
                            label = "Participantes Máximos",
                            value = maxParticipants,
                            onValueChange = { maxParticipants = it }, // 🔹 Se actualiza el estado
                            fieldType = FieldType.Text,
                            leadingIcon = {
                                Icon(Icons.Default.Group, contentDescription = "Participantes")
                            }
                        )


                        if ((maxParticipants.toIntOrNull() ?: 0) in 1..10 && invitedFriends.size > maxParticipants.toInt()) {
                            Text(
                                text = " Hay más invitados que el número máximo de participantes.",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))

                        // **Botón para agregar amigos**
                        ButtonPrimary(
                            text = "Agregar Amigos",
                            onClick = { navController.navigate(Routes.FRIENDS) },
                            modifier = Modifier.fillMaxWidth()
                        )

                        //  **Mostrar nombres de amigos invitados**
                        if (invitedFriends.isNotEmpty()) {
                            Column(modifier = Modifier.padding(top = 8.dp)) {
                                Text(
                                    text = "Invitados:",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )

                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    invitedFriends.forEach { friend ->
                                        AssistChip(
                                            onClick = {},
                                            label = { Text(friend, color = Color.White) },
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = Icons.Default.Person,
                                                    contentDescription = null,
                                                    tint = Color.White
                                                )
                                            },
                                            colors = AssistChipDefaults.assistChipColors(
                                                containerColor = Color(0xFF2E7D32)
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    ButtonPrimary(
                        text = "Siguiente",
                        onClick = {
                            if (dateTime != null && selectedCenter != null) {
                                val timestamp = Timestamp(dateTime!!) // ✅ Convertimos correctamente la fecha
                                navController.navigate(
                                    "event_summary/${sportId}/${timestamp.seconds}/${selectedCenter!!.id}/${maxParticipants}/${invitedFriends.joinToString(",")}"
                                )
                            }
                        },
                        enabled = dateTime != null && selectedCenter != null
                    )
                }
            }
        }
    }
}



*/