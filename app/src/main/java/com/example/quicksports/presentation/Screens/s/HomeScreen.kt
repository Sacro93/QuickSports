package com.example.quicksports.presentation.Screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.quicksports.Screen
import com.example.quicksports.R

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EventCard(
                imageResId = R.drawable.ic_launcher_foreground,
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
