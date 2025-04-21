package com.example.quicksports.presentation.Navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quicksports.data.preferences.PreferenceManager
import com.example.quicksports.data.repository.AuthRepository
import com.example.quicksports.presentation.Screens.CrearEventoScreen
import com.example.quicksports.presentation.Screens.HomeScreen
import com.example.quicksports.presentation.Screens.Principales.Centros.CentrosScreen
import com.example.quicksports.presentation.Screens.Principales.Evento.CrearEventoPaso2Screen
import com.example.quicksports.presentation.Screens.Principales.Evento.EventosZonaScreen
import com.example.quicksports.presentation.Screens.Principales.Evento.TusEventosScreen
import com.example.quicksports.presentation.Screens.Principales.Faqs.FaqScreen
import com.example.quicksports.presentation.Screens.Principales.Friends.AmistadesScreen
import com.example.quicksports.presentation.Screens.Principales.Friends.FriendSelectorScreen
import com.example.quicksports.presentation.Screens.Principales.Login_Register.LoginScreen
import com.example.quicksports.presentation.Screens.Principales.Login_Register.RegisterScreen
import com.example.quicksports.presentation.Screens.Principales.Login_Register.StartScreen
import com.example.quicksports.presentation.Screens.Principales.Notificaciones.NotificationsScreen
import com.example.quicksports.presentation.Screens.Principales.Profile.EditarPerfilScreen
import com.example.quicksports.presentation.Screens.Principales.Profile.PerfilScreen
import com.example.quicksports.presentation.ViewModel.Center.CenterViewModelFactory
import com.example.quicksports.presentation.ViewModel.Eventos.CrearEventoViewModel
import com.example.quicksports.presentation.ViewModel.Eventos.CrearEventoViewModelFactory
import com.example.quicksports.presentation.ViewModel.Eventos.EventosZonaViewModelFactory
import com.example.quicksports.presentation.ViewModel.Eventos.TusEventosViewModelFactory
import com.example.quicksports.presentation.ViewModel.Friends.FriendsViewModelFactory
import com.example.quicksports.presentation.ViewModel.Login.LoginViewModel
import com.example.quicksports.presentation.ViewModel.Login.LoginViewModelFactory
import com.example.quicksports.presentation.ViewModel.Register.RegisterViewModel
import com.example.quicksports.presentation.ViewModel.Register.RegisterViewModelFactory
import com.example.quicksports.presentation.ViewModel.Sports.SportsViewModelFactory
import com.example.quicksports.presentation.ViewModel.User.UserViewModelFactory
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
            val registerViewModel: RegisterViewModel = viewModel(
                factory = RegisterViewModelFactory(AuthRepository())
            )
            RegisterScreen(
                navController = navController,
                registerViewModel = registerViewModel
            )
        }

        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(AuthRepository())
            )
            LoginScreen(
                navController = navController,
                viewModel = loginViewModel
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.FAQ.route) {
            FaqScreen(navController)
        }


        composable(Screen.NotificationsScreen.route) {
            NotificationsScreen(navController = navController)
        }
        composable(Screen.CrearEvento.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.CrearEvento.route)
            }
            val crearEventoViewModel: CrearEventoViewModel = viewModel(
                parentEntry,
                factory = CrearEventoViewModelFactory()
            )
            CrearEventoScreen(navController, crearEventoViewModel)
        }

        composable(Screen.CrearEventoPaso2.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.CrearEvento.route)
            }
            val crearEventoViewModel: CrearEventoViewModel = viewModel(
                parentEntry,
                factory = CrearEventoViewModelFactory()
            )
            CrearEventoPaso2Screen(navController, crearEventoViewModel)
        }

        composable(Screen.FriendSelector.route) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.CrearEvento.route)
            }
            val crearEventoViewModel: CrearEventoViewModel = viewModel(
                parentEntry,
                factory = CrearEventoViewModelFactory()
            )
            FriendSelectorScreen(navController, crearEventoViewModel)
        }

        composable(Screen.TusEventos.route) {
            TusEventosScreen(
                navController = navController,
                viewModel = viewModel(factory = TusEventosViewModelFactory(LocalContext.current.applicationContext as Application))
            )
        }

        composable(Screen.EventosZona.route) {
            EventosZonaScreen(
                navController = navController,
                viewModel = viewModel(factory = EventosZonaViewModelFactory())
            )
        }

        composable(Screen.Amistades.route) {
            AmistadesScreen(
                navController,
                friendsViewModel = viewModel(factory = FriendsViewModelFactory(LocalContext.current.applicationContext as Application))
            )
        }

        composable(Screen.Centros.route) {
            CentrosScreen(
                navController,
                centerViewModel = viewModel(factory = CenterViewModelFactory(LocalContext.current.applicationContext as Application)),
                sportsViewModel = viewModel(factory = SportsViewModelFactory(LocalContext.current.applicationContext as Application))
            )
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(
                navController = navController,
                userViewModel = viewModel(factory = UserViewModelFactory(AuthRepository())),
                onEditProfileClick = { navController.navigate(Screen.EditarPerfil.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.EditarPerfil.route) {
            EditarPerfilScreen(
                userViewModel = viewModel(factory = UserViewModelFactory(AuthRepository())),
                navController = navController
            )
        }
    }
}
