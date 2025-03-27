package com.example.quicksports.presentation.Screens.s.Evento


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.presentation.Screens.BottomNavigationBar
import com.example.quicksports.presentation.ViewModel.EventosZonaViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventosZonaScreen(viewModel: EventosZonaViewModel = viewModel(),  navController: NavController,) {
    val context = LocalContext.current
    val eventos = viewModel.eventosZona.collectAsState().value

    // Estado local para saber a qué eventos ya se envió solicitud
    val solicitudesEnviadas = remember { mutableStateMapOf<Int, Boolean>() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Eventos de tu zona") })
        },
        bottomBar = {
            BottomNavigationBar(navController = navController) // Asegúrate de pasar tu NavController real
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
            .verticalScroll(rememberScrollState())

            .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            eventos.forEach { evento ->
                val solicitudEnviada = solicitudesEnviadas[evento.hashCode()] == true

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Deporte: ${evento.deporte}", style = MaterialTheme.typography.titleMedium)
                        Text("Centro: ${evento.centro}")
                        Text("Dirección: ${evento.direccion}")
                        Text("Organizador: ${evento.creador}")
                        Text("Fecha y hora: ${evento.fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                solicitudesEnviadas[evento.hashCode()] = true
                                Toast.makeText(context, "Solicitud enviada", Toast.LENGTH_SHORT).show()
                            },
                            enabled = !solicitudEnviada
                        ) {
                            Text(if (solicitudEnviada) "Solicitud enviada" else "Unirme")
                        }
                    }
                }
            }
        }
    }
}
