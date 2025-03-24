package com.example.quicksports

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quicksports.presentation.Screens.CrearEventoScreen
import com.example.quicksports.presentation.Screens.HomeScreen
import com.example.quicksports.presentation.Screens.s.Evento.CrearEventoPaso2Screen
import com.example.quicksports.presentation.Screens.s.Evento.EventosZonaScreen
import com.example.quicksports.presentation.Screens.s.Evento.TusEventosScreen
import com.example.quicksports.presentation.Screens.s.Friends.FriendSelectorScreen
import com.example.quicksports.presentation.ViewModel.CrearEventoViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        // CrearEvento - PASO 1
        composable(Screen.CrearEvento.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.CrearEvento.route)
            }
            val crearEventoViewModel: CrearEventoViewModel = viewModel(parentEntry)
            CrearEventoScreen(navController, crearEventoViewModel)
        }

        // CrearEvento - PASO 2
        composable(Screen.CrearEventoPaso2.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.CrearEvento.route)
            }
            val crearEventoViewModel: CrearEventoViewModel = viewModel(parentEntry)
            CrearEventoPaso2Screen(navController, crearEventoViewModel)
        }

        // Selector de amigos
        composable(Screen.FriendSelector.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.CrearEvento.route)
            }
            val crearEventoViewModel: CrearEventoViewModel = viewModel(parentEntry)
            FriendSelectorScreen(navController, crearEventoViewModel)
        }

        composable(Screen.TusEventos.route) {
            TusEventosScreen()
        }

        composable(Screen.EventosZona.route) {
            EventosZonaScreen()
        }
    }
}
