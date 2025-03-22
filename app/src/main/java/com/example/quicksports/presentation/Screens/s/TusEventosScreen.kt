package com.example.quicksports.presentation.Screens.s


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quicksports.presentation.ViewModel.CrearEventoViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TusEventosScreen(viewModel: CrearEventoViewModel = viewModel()) {
    val evento = viewModel.armarEvento()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Tus eventos") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (evento != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Deporte: ${evento.deporte.name}", style = MaterialTheme.typography.titleMedium)
                        Text("Centro: ${evento.centro.name}")
                        Text("Dirección: ${evento.centro.address}")
                        Text("Teléfono centro: ${evento.centro.contactPhone}")
                        Text("Fecha y hora: ${evento.fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
                        Text("Máx. participantes: ${evento.maxParticipantes}")

                        Spacer(modifier = Modifier.height(8.dp))

                        if (evento.amigosInvitados.isNotEmpty()) {
                            Text("Amigos invitados:", style = MaterialTheme.typography.titleMedium)
                            evento.amigosInvitados.forEach {
                                Text("- ${it.name} (${it.phone})")
                            }
                        } else {
                            Text("Sin amigos invitados.")
                        }
                    }
                }
            } else {
                Text("Aún no has creado ningún evento.")
            }
        }
    }
}
