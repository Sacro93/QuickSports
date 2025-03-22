package com.example.quicksports

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quicksports.presentation.Screens.CrearEventoScreen
import com.example.quicksports.presentation.Screens.HomeScreen
import com.example.quicksports.presentation.Screens.s.CrearEventoPaso2Screen
import com.example.quicksports.presentation.Screens.s.EventosZonaScreen
import com.example.quicksports.presentation.Screens.s.TusEventosScreen
import com.example.quicksports.presentation.components.FriendSelectorScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.CrearEvento.route) { CrearEventoScreen(navController) }
        composable(Screen.CrearEventoPaso2.route) { CrearEventoPaso2Screen(navController) }
        composable(Screen.FriendSelector.route) { FriendSelectorScreen(navController) }
        composable(Screen.TusEventos.route) {
            TusEventosScreen()
        }
        composable(Screen.EventosZona.route) {
            EventosZonaScreen()
        }


    }
}
