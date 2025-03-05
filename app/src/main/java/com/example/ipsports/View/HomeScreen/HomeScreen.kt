package com.example.ipsports.View.HomeScreen

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ipsports.R
import com.example.ipsports.View.Reusable.BottomNavigationBar
import com.example.ipsports.View.Reusable.FeatureCard
import com.example.ipsports.View.theme.Font.QuickSportsTitleGradient
import com.example.ipsports.ViewModel.ui.EventViewModel
import com.example.ipsports.data.DatosDefault.populateFirestore
import com.example.ipsports.data.DatosDefault.populateUsers
import com.example.ipsports.data.routesNavigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
    currentRoute: String,
    onNavigate: (String) -> Unit,

    ) {

    val viewModel: EventViewModel = hiltViewModel()

    val eventViewModel: EventViewModel = hiltViewModel()
    val latestEvent by eventViewModel.latestEvent.collectAsStateWithLifecycle()
    val events by viewModel.eventsList.collectAsStateWithLifecycle()

    val isLoading by eventViewModel.isLoading.collectAsStateWithLifecycle()

    // 🔹 Obtener el userId del usuario autenticado
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    // 🔹 Cargar eventos solo si userId no es nulo
    LaunchedEffect(userId) {
        userId?.let { eventViewModel.loadEventsByUser(it) }
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = onNavigate,

                )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1E88E5), // Azul brillante (inicio)
                            Color(0xFF1565C0), // Azul medio
                            Color(0xFF000000)  // Negro (final)
                        )
                    )
                )
                .padding(padding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(24.dp))

            //  **Nombre de la App**
            QuickSportsTitleGradient()

            Spacer(modifier = Modifier.height(24.dp))

            //  **Tarjetas de eventos**
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FeatureCard(
                    imageRes = R.drawable.brc,
                    title = "Create Your Own Event",
                    onClick = { navController.navigate(Routes.SPORT_SELECTION) }
                )
                FeatureCard(
                    imageRes = R.drawable.grupo,
                    title = "Find Your Sport Match",
                    onClick = { /* Acción de ejemplo */ }
                )

                FeatureCard(
                    imageRes = R.drawable.grupo,
                    title = "Active Events",
                    onClick = { navController.navigate(Routes.ACTIVE_EVENTS) } // ✅ Ahora lleva a eventos activos
                )
                // 🔹 Mostrar evento más reciente si existe
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    latestEvent?.let { event ->
                        EventCard(
                            sport = event.sportId,
                            date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(event.date.toDate()),
                            location = event.centerId,
                            participants = event.usersInvited,
                            maxParticipants = event.maxParticipants,
                            modifier = Modifier.padding(16.dp)
                        )
                    } ?: Text("No hay eventos recientes", color = Color.White)
                }
            }
        }
    }

    //  DebugFirestoreScreen(firestore = FirebaseFirestore.getInstance())

   // Spacer(modifier = Modifier.height(24.dp))

   // DebugFirestoreScreen2(firestore = FirebaseFirestore.getInstance())
}





@Composable
fun DebugFirestoreScreen(firestore: FirebaseFirestore) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                populateFirestore(firestore)
            }
        }) {
            Text("🔄 Poblar Firestore")
        }
    }
}


@Composable
fun DebugFirestoreScreen2(firestore: FirebaseFirestore) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                populateUsers(firestore)
            }
        }) {
            Text("🔄 Poblar Firestore")
        }
    }
}