package com.example.quicksports

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quicksports.data.PreferenceManager
import com.example.quicksports.presentation.Screens.CrearEventoScreen
import com.example.quicksports.presentation.Screens.HomeScreen
import com.example.quicksports.presentation.Screens.s.Centros.CentrosScreen
import com.example.quicksports.presentation.Screens.s.Evento.CrearEventoPaso2Screen
import com.example.quicksports.presentation.Screens.s.Evento.EventosZonaScreen
import com.example.quicksports.presentation.Screens.s.Evento.TusEventosScreen
import com.example.quicksports.presentation.Screens.s.Friends.AmistadesScreen
import com.example.quicksports.presentation.Screens.s.Friends.FriendSelectorScreen
import com.example.quicksports.presentation.Screens.s.Login_Register.LoginScreen
import com.example.quicksports.presentation.Screens.s.Login_Register.RegisterScreen
import com.example.quicksports.presentation.Screens.s.Login_Register.StartScreen
import com.example.quicksports.presentation.Screens.s.Profile.EditarPerfilScreen
import com.example.quicksports.presentation.Screens.s.Profile.PerfilScreen
import com.example.quicksports.presentation.ViewModel.CrearEventoViewModel
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val startDestination = remember {
        if (FirebaseAuth.getInstance().currentUser != null &&
            PreferenceManager.shouldKeepLoggedIn(context)
        ) {
            Screen.Home.route
        } else {
            Screen.Start.route
        }
    }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Start.route) {
            StartScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        // Login
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
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

        // Amistades
        composable(Screen.Amistades.route) {
            AmistadesScreen()
        }

        // Centros deportivos
        composable(Screen.Centros.route) {
            CentrosScreen()
        }

        // Perfil
        composable(Screen.Perfil.route) {
            PerfilScreen(
                onEditProfileClick = { navController.navigate(Screen.EditarPerfil.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.EditarPerfil.route) {
            EditarPerfilScreen(navController = navController)
        }

    }
}
