package com.example.ipsports.View.Event.pg2

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ipsports.ViewModel.ui.EventInfoViewModel
import com.example.ipsports.data.routesNavigation.Routes
import com.example.ipsports.data.showDateTimePicker

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
 fun EventInfoScreen(
    sportId: String,
    navController: NavController,
    eventInfoViewModel: EventInfoViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val uiState by eventInfoViewModel.uiState.collectAsStateWithLifecycle()
    val centersWithSports by eventInfoViewModel.centersWithSports.collectAsStateWithLifecycle()

    //  Obtener el nombre del deporte
    val sportName = eventInfoViewModel.getSportName(sportId)

    // Filtrar centros deportivos disponibles para el deporte seleccionado
    val availableCenters = remember(centersWithSports, sportId) {
        centersWithSports.filter { it.sports.any { sport -> sport.id == sportId } }
            .map { it.center }
    }

    val context = LocalContext.current

    //  Cargar centros filtrados al iniciar la pantalla
    LaunchedEffect(sportId) {
        eventInfoViewModel.loadCentersForSport(sportId)
    }

    //  Recuperar amigos invitados al volver de la pantalla de selección
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
                        colors = listOf(Color(0xFF337C8D), Color(0xFF15272D), Color(0xFF17272B))
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

                        EventInputField(
                            label = "Fecha y Hora del Evento",
                            value = uiState.dateTime?.let {
                                SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(it)
                            } ?: "",
                            onValueChange = {},
                            fieldType = FieldType.Date,
                            leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Fecha y Hora") },
                            onClick = {
                                showDateTimePicker(context) { date ->
                                    eventInfoViewModel.updateDate(date)
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        //  Selección del centro deportivo
                        OptionCenters(
                            selectedCenter = uiState.selectedCenter,
                            centers = availableCenters,
                            onCenterSelected = { eventInfoViewModel.updateSelectedCenter(it) }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        //  Participantes máximos
                        EventInputField(
                            label = "Participantes Máximos",
                            value = uiState.maxParticipants.toString(),
                            onValueChange = { value ->
                                value.toIntOrNull()?.let { eventInfoViewModel.updateMaxParticipants(it) }
                            },
                            fieldType = FieldType.Text,
                            leadingIcon = { Icon(Icons.Default.Group, contentDescription = "Participantes") }
                        )

                        if ((uiState.maxParticipants ?: 0) in 1..10 &&
                            uiState.invitedFriends.size > (uiState.maxParticipants ?: 0)) {
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
                                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.White) },
                                            colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFF2E7D32))
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



