package com.example.ipsports.View.Event.pg3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Place
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Sports
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ipsports.View.Event.ReusableEvent.EventCreationProgressBar
import com.example.ipsports.View.Reusable.ButtonPrimary
import com.example.ipsports.ViewModel.ui.EventViewModel
import com.example.ipsports.data.model.Event
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ipsports.ViewModel.ui.EventInfoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EventSummaryScreen(
    sportId: String,
    timestamp: Long,
    centerId: String,
    maxParticipants: Int,
    invitedFriends: List<String>,
    navController: NavController,
    onBack: () -> Unit = {}
) {
    val eventViewModel: EventViewModel = hiltViewModel()
    val eventInfoViewModel: EventInfoViewModel = hiltViewModel()

    val isLoading by eventViewModel.isLoading.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // ✅ Convertir `timestamp` a `Date`
    val date = remember { Date(timestamp * 1000) }
    val formattedDate = remember(date) { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(date) }

    // ✅ Obtener nombres del deporte y centro
    val sportName by produceState(initialValue = "Cargando...") {
        value = eventInfoViewModel.getSportName(sportId)
    }
    val centerName by produceState(initialValue = "Cargando...") {
        value = eventInfoViewModel.getCenterName(centerId)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Resumen del Evento", color = Color.White) },
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
                Spacer(modifier = Modifier.height(16.dp))
                EventCreationProgressBar(currentPage = 4, totalPages = 4)
                Spacer(modifier = Modifier.height(35.dp))

                // 🔹 Tarjeta de resumen del evento
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Resumen del Evento",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFFBFD7EA)
                        )

                        InfoRow("Deporte", sportName, Icons.Default.Sports)
                        InfoRow("Fecha", formattedDate, Icons.Default.CalendarToday)
                        InfoRow("Lugar", centerName, Icons.Default.Place)
                        InfoRow("Máx. Participantes", maxParticipants.toString(), Icons.Default.People)

                        Text(
                            text = "Amigos Invitados:",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        if (invitedFriends.isNotEmpty()) {
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
                                                tint = Color(0xFFBFD7EA)
                                            )
                                        },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = Color(0xFF2E7D32)
                                        )
                                    )
                                }
                            }
                        } else {
                            Text("Ninguno", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    ButtonPrimary(
                        text = "Confirmar Evento",
                        onClick = {
                            val event = Event(
                                sportId = sportId,
                                centerId = centerId,
                                userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                                date = Timestamp(date), // ✅ Fecha en formato correcto para Firestore
                                maxParticipants = maxParticipants,
                                usersInvited = invitedFriends,
                                status = "pending"
                            )

                            eventViewModel.addEvento(event) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("✅ ¡Evento creado exitosamente!")
                                    navController.navigate("home")
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(250.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF76A9A0)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("$label: $value", style = MaterialTheme.typography.bodyLarge, color = Color.White)
    }
}
