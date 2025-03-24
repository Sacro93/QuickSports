package com.example.quicksports.presentation.Screens.s.Centros
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quicksports.presentation.ViewModel.CenterViewModel
import com.example.quicksports.presentation.ViewModel.SportsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CentrosScreen(
    centerViewModel: CenterViewModel = viewModel(),
    sportsViewModel: SportsViewModel = viewModel()
) {
    val centros by centerViewModel.centros.collectAsState()
    val deportes by sportsViewModel.sports.collectAsState()
    var deporteSeleccionado by remember { mutableStateOf<Int?>(null) }
    val context = LocalContext.current

    val centrosFiltrados = remember(deporteSeleccionado, centros) {
        deporteSeleccionado?.let { deporteId ->
            centros.filter { it.sportPrices.containsKey(deporteId) }
        } ?: centros
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Centros Deportivos", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                FilterChip(
                    selected = deporteSeleccionado == null,
                    onClick = { deporteSeleccionado = null },
                    label = { Text("Todos") }
                )
            }
            items(deportes) { deporte ->
                FilterChip(
                    selected = deporteSeleccionado == deporte.id,
                    onClick = { deporteSeleccionado = deporte.id },
                    label = { Text(deporte.name) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(centrosFiltrados) { centro ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(centro.name, style = MaterialTheme.typography.titleMedium)
                            IconButton(onClick = {
                                centerViewModel.toggleFavorite(centro)
                                Toast.makeText(
                                    context,
                                    if (!centro.isFavorite) "Agregado a favoritos" else "Quitado de favoritos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {
                                Icon(
                                    imageVector = if (centro.isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                                    contentDescription = "Favorito"
                                )

                            }
                        }
                        Text(centro.address)
                        Text("Tel: ${centro.contactPhone}")
                        Spacer(modifier = Modifier.height(8.dp))
                        centro.sportPrices.forEach { (sportId, precio) ->
                            val deporte = deportes.find { it.id == sportId }?.name ?: "Deporte"
                            Text("$deporte: €$precio")
                        }
                    }
                }
            }
        }
    }
}
