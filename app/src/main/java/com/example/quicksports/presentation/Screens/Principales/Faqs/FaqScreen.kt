package com.example.quicksports.presentation.Screens.Principales.Faqs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quicksports.data.models.FaqItem
 import androidx.navigation.NavController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(
    navController: NavController
) {
    val faqs = remember {
        listOf(
            FaqItem("Reglas del fútbol", "Cada equipo tiene 11 jugadores. Gana quien marque más goles. El fuera de juego y las faltas son clave."),
            FaqItem("Reglas del básquet", "Cada equipo tiene 5 jugadores. Gana quien más puntos haga lanzando la pelota al aro. Cada partido tiene 4 cuartos de 10 minutos."),
            FaqItem("Reglas del tenis", "Se juega 1 contra 1 o 2 contra 2. Se gana por sets, y cada set por games. Hay saque, red y líneas de fondo."),
            FaqItem("Reglas del pádel", "Similar al tenis pero se juega en cancha cerrada con paredes. Siempre en parejas. Se permite el rebote en paredes."),
            FaqItem("Normas de convivencia", "Respetá a tus compañeros, llegá puntual y no insultes. Quick Sports promueve el juego limpio y la buena onda.")
        )
    }

    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "❓ Preguntas Frecuentes",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF3F51B5))
            )
        },
        containerColor = Color(0xFF1A1A2E)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(faqs) { index, faq ->
                val expanded = expandedStates[index] ?: false
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF3949AB))
                        .clickable { expandedStates[index] = !expanded }
                        .padding(16.dp)
                ) {
                    Text(faq.question, color = Color.White, fontWeight = FontWeight.Bold)
                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(faq.answer, color = Color.White.copy(alpha = 0.9f))
                    }
                }
            }
        }
    }
}
