package com.example.quicksports.presentation.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.data.models.Sport
import com.example.quicksports.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.example.quicksports.Screen
import com.example.quicksports.presentation.ViewModel.CrearEventoViewModel
@Composable
fun CrearEventoScreen(navController: NavController, viewModel: CrearEventoViewModel = viewModel()) {
    val selectedSport by viewModel.selectedSport.collectAsState()
    val selectedCenter by viewModel.selectedCenter.collectAsState()

    val sports = listOf(
        Sport(1, "Fútbol", R.drawable.futbol),
        Sport(2, "Básquet", R.drawable.basquet),
        Sport(3, "Tenis", R.drawable.tenis),
        Sport(4, "Pádel", R.drawable.padel)
    )

    val filteredCenters by remember(selectedSport, viewModel.centers) {
        mutableStateOf(viewModel.getFilteredCenters())
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Selecciona un deporte", style = MaterialTheme.typography.headlineSmall)

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sports) { sport ->
                    Column(
                        modifier = Modifier
                            .clickable { viewModel.selectSport(sport) }
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = sport.imageRes),
                            contentDescription = sport.name,
                            modifier = Modifier.size(72.dp)
                        )
                        Text(
                            text = sport.name,
                            color = if (selectedSport == sport) Color.Cyan else Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (selectedSport != null) {
                Text("Centros disponibles para ${selectedSport?.name}", style = MaterialTheme.typography.titleMedium)

                Column(modifier = Modifier.padding(16.dp)) {
                    filteredCenters.forEach { center ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { viewModel.selectCenter(center) },
                            border = if (viewModel.isCentroSeleccionado(center)) BorderStroke(2.dp, Color.Cyan) else null,
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = center.name, style = MaterialTheme.typography.titleMedium, color = Color.White)
                                Text(text = center.address, color = Color.LightGray)
                                Text(text = "Tel: ${center.contactPhone}", color = Color.LightGray)
                                selectedSport?.id?.let { sportId ->
                                    center.sportPrices[sportId]?.let { price ->
                                        Text(text = "Precio: €$price", color = Color.Cyan)
                                    }
                                }
                            }
                        }
                    }

                    if (filteredCenters.isNotEmpty() && selectedCenter != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            // Redirigir a la segunda pantalla de creación
                            navController.navigate(Screen.CrearEventoPaso2.route)
                        }) {
                            Text("Siguiente")
                        }
                    }
                }
            }
        }
    }
}
