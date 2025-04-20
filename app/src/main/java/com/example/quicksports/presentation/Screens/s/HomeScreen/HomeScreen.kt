package com.example.quicksports.presentation.Screens


import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quicksports.presentation.Navigation.Screen
import com.example.quicksports.R
import com.example.quicksports.data.defaulData.DefaultFriends
import com.example.quicksports.presentation.CarouselPublicidadRes
import com.example.quicksports.presentation.ViewModel.Friends.FriendsViewModel
import com.example.quicksports.presentation.components.QuickSportsTitle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
        Box(
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
        ) {
            // Ícono de notificaciones con badge animado (arriba a la derecha)
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp)
            ) {
                IconButton(onClick = { navController.navigate(Screen.NotificationsScreen.route) }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                val scale = remember { Animatable(1f) }

                LaunchedEffect(Unit) {
                    while (true) {
                        scale.animateTo(
                            targetValue = 1.2f,
                            animationSpec = tween(durationMillis = 500)
                        )
                        scale.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(durationMillis = 500)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .scale(scale.value)
                        .background(Color.Red, CircleShape)
                        .align(Alignment.TopEnd)
                        .offset(x = (-2).dp, y = 2.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                QuickSportsTitle()

                Spacer(modifier = Modifier.height(70.dp))

                CarouselPublicidadRes(
                    images = listOf(
                        R.drawable.publicidad_1,
                        R.drawable.publicidad_2,
                        R.drawable.publicidad_3
                    )
                )

                Spacer(modifier = Modifier.height(70.dp))

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
}





@Composable
fun ResetFriendsButton(friendsViewModel: FriendsViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                val defaultFriends = DefaultFriends.get()
                friendsViewModel.reemplazarAmigos(defaultFriends)
                Toast.makeText(context, "Amigos restaurados correctamente", Toast.LENGTH_SHORT).show()
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFD32F2F),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text("Restablecer amigos por defecto")
    }
}
