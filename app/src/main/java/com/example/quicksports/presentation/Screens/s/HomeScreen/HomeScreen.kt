package com.example.quicksports.presentation.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quicksports.Screen
import com.example.quicksports.R
import com.example.quicksports.presentation.components.QuickSportsTitle

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController,
                containerColor = Color.Transparent // Cambio a transparente
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2B6070), // Verde azulado medio
                            Color(0xFF1F3F4C), // Azul oscuro con verde
                            Color(0xFF162F3C),
                            Color(0xFF0F1D20)  // Combinando con AmistadesScreen
                        )
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))
            QuickSportsTitle()
            Spacer(modifier = Modifier.height(32.dp))

            EventCard(
                imageResId = R.drawable.grupo,
                title = "Crear evento",
                onClick = { navController.navigate(Screen.CrearEvento.route) }
            )

            EventCard(
                imageResId = R.drawable.tus_eventos,
                title = "Tus eventos",
                onClick = { navController.navigate(Screen.TusEventos.route) }
            )

            EventCard(
                imageResId = R.drawable.eventos_zona,
                title = "Eventos de tu zona",
                onClick = { navController.navigate(Screen.EventosZona.route) }
            )
        }
    }
}
