package com.example.quicksports.presentation.Screens.s


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quicksports.presentation.ViewModel.EventosZonaViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventosZonaScreen(viewModel: EventosZonaViewModel = viewModel()) {
    val context = LocalContext.current
    val eventos = viewModel.eventosZona.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Eventos de tu zona") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            eventos.forEach { evento ->
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

                        Button(onClick = {
                            Toast.makeText(context, "Solicitud de unirse al evento enviada", Toast.LENGTH_SHORT).show()
                        }) {
                            Text("Unirme")
                        }
                    }
                }
            }
        }
    }
}