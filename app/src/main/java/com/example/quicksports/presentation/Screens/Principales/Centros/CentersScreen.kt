package com.example.quicksports.presentation.Screens.Principales.Centros
import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.presentation.Screens.BottomNavigationBar
import com.example.quicksports.presentation.ViewModel.Center.CenterViewModel
import com.example.quicksports.presentation.ViewModel.Center.CenterViewModelFactory
import com.example.quicksports.presentation.ViewModel.Sports.SportsViewModel
import com.example.quicksports.presentation.ViewModel.Sports.SportsViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CentersScreen(
    navController: NavController,
    centerViewModel: CenterViewModel = viewModel(
        factory = CenterViewModelFactory(LocalContext.current.applicationContext as Application)
    ),
    sportsViewModel: SportsViewModel = viewModel(
        factory = SportsViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    val centers by centerViewModel.centers.collectAsState()
    val sports by sportsViewModel.sports.collectAsState()
    var sportSelected by remember { mutableStateOf<Int?>(null) }
    val context = LocalContext.current

    val filteredCenters = remember(sportSelected, centers) {
        sportSelected?.let { sportId ->
            centers.filter { it.sportPrices.containsKey(sportId) }
        } ?: centers
    }


    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = Color(0xFF000000)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1976D2),
                            Color(0xFF0D47A1),
                            Color(0xFF000000)
                        )
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = "Centros Deportivos",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    FilterChip(
                        selected = sportSelected == null,
                        onClick = { sportSelected = null },
                        label = { Text("Todos") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color.White.copy(alpha = 0.15f),
                            labelColor = Color.White
                        )
                    )
                }
                items(sports) { sport ->
                    FilterChip(
                        selected = sportSelected == sport.id,
                        onClick = { sportSelected = sport.id },
                        label = { Text(sport.name) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color.White.copy(alpha = 0.15f),
                            labelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                items(filteredCenters) { centro ->
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C3E50)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    centro.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp
                                    )
                                )
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
                                        contentDescription = "Favorito",
                                        tint = if (centro.isFavorite) Color(0xFFFFD54F) else Color.White
                                    )
                                }
                            }

                            Text(
                                centro.address,
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 14.sp
                            )

                            Text(
                                "Tel: ${centro.contactPhone}",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 13.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            centro.sportPrices.forEach { (sportId, precio) ->
                                val sportName = sports.find { it.id == sportId }?.name ?: "Deporte"
                                Text(
                                    text = "$sportName – €$precio / hora por cancha",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.White.copy(alpha = 0.9f),
                                        fontSize = 14.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
