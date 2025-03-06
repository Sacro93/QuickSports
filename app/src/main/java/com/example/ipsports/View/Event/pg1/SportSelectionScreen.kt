package com.example.ipsports.View.Event.pg1
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ipsports.View.Event.ReusableEvent.EventCreationProgressBar
import com.example.ipsports.View.Reusable.ButtonPrimary
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ipsports.ViewModel.ui.SportViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportSelectionScreen(
    navController: NavController,
    onBack: () -> Unit
) {
    val viewModel: SportViewModel = hiltViewModel()
    val sportsList by viewModel.sports.collectAsStateWithLifecycle()
    val selectedSport by viewModel.selectedSport.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    // ✅ Evita recargar la lista de deportes en cada recomposición
    LaunchedEffect(Unit) {
        viewModel.loadSports()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selecciona un Deporte", color = Color.White) },
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
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                EventCreationProgressBar(currentPage = 2, totalPages = 4)

                Spacer(modifier = Modifier.height(50.dp))

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(sportsList) { sport ->
                            SportSelectionCard(
                                sport = sport,
                                isSelected = selectedSport?.id == sport.id,
                                onClick = { viewModel.selectSport(sport) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ButtonPrimary(
                        text = "Continuar",
                        onClick = {
                            selectedSport?.let { sport ->
                                navController.navigate("event_info/${sport.id}")
                            }
                        },
                        enabled = selectedSport != null,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(280.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}




