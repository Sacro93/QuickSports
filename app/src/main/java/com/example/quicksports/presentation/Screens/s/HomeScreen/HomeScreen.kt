package com.example.quicksports.presentation.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quicksports.Screen
import com.example.quicksports.R
import com.example.quicksports.presentation.components.QuickSportsTitle
import kotlinx.coroutines.delay



@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                containerColor = Color.Transparent
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
                            Color(0xFF2B6070),
                            Color(0xFF1F3F4C),
                            Color(0xFF162F3C),
                            Color(0xFF0F1D20)
                        )
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(56.dp))
            QuickSportsTitle()
            Spacer(modifier = Modifier.height(85.dp))

            CarouselPublicidadRes(
                images = listOf(
                    R.drawable.publicidad_1,
                    R.drawable.publicidad_2,
                    R.drawable.publicidad_3
                )
            )


            Spacer(modifier = Modifier.height(100.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                EventCard(
                    imageResId = R.drawable.eventos_zona,
                    title = "Eventos de tu zona",
                    onClick = { navController.navigate(Screen.EventosZona.route) },
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )

                EventCard(
                    imageResId = R.drawable.tus_eventos,
                    title = "Tus eventos",
                    onClick = { navController.navigate(Screen.TusEventos.route) },
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            EventCard(
                imageResId = R.drawable.grupo,
                title = "Crear evento",
                onClick = { navController.navigate(Screen.CrearEvento.route) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CarouselPublicidadRes(images: List<Int>) {
    val pagerState = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            pagerState.intValue = (pagerState.intValue + 1) % images.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Gray.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = images[pagerState.intValue]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}






/*
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
*/
