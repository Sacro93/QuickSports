package com.example.ipsports.View.Navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ipsports.View.ActiveEventsScreen
import com.example.ipsports.View.CentersScreen
import com.example.ipsports.View.Event.pg1.SportSelectionScreen
import com.example.ipsports.View.Event.pg2.EventInfoScreen
import com.example.ipsports.View.Event.pg3.EventSummaryScreen
import com.example.ipsports.View.Event.pg4.AddFriendsScreen
import com.example.ipsports.View.HomeScreen.HomeScreen
import com.example.ipsports.View.Login.LoginEntryScreen
import com.example.ipsports.View.Login.LoginScreen
import com.example.ipsports.View.Profile.EditProfileScreen
import com.example.ipsports.View.Profile.ProfileScreen
import com.example.ipsports.View.Register.RegisterScreen
import com.example.ipsports.ViewModel.Autenticacion.AuthViewModel
import com.example.ipsports.ViewModel.ui.UserViewModel
import com.example.ipsports.data.routesNavigation.Routes

@Composable
fun Navigation(
    navController: NavHostController
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val isLoggedIn by authViewModel.isUserLoggedIn.collectAsStateWithLifecycle()

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(Routes.LOGIN_ENTRY) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Routes.HOME else Routes.LOGIN_ENTRY
    ) {
        composable(Routes.LOGIN_ENTRY) {
            LoginEntryScreen(
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) },
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                authViewModel = hiltViewModel(),
                onNavigateToHome = { navController.navigate(Routes.HOME) },
                onRegisterClick = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                authViewModel = hiltViewModel(),
                navController = navController,
                onBack = {
                    if (!navController.popBackStack(Routes.LOGIN_ENTRY, inclusive = false)) {
                        navController.navigate(Routes.LOGIN_ENTRY) {
                            popUpTo(Routes.LOGIN_ENTRY) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                currentRoute = Routes.HOME,
                onNavigate = { route -> navController.navigate(route) },
                navController = navController
            )

        }

        // **Pantalla de Selección de Deportes**
        composable(Routes.SPORT_SELECTION) {
            SportSelectionScreen(
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }

        //  **Pantalla de Información del Evento**
        composable("event_info/{selectedSport}") { backStackEntry ->
            val selectedSport = backStackEntry.arguments?.getString("selectedSport") ?: return@composable

            EventInfoScreen(
                sportId = selectedSport,
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.FRIENDS) {
            AddFriendsScreen(navController = navController)
        }

//  Pantalla de Resumen del Evento
        composable("event_summary/{sport}/{date}/{location}/{maxParticipants}/{friends}") { backStackEntry ->
            val sport = backStackEntry.arguments?.getString("sport") ?: ""
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val location = backStackEntry.arguments?.getString("location") ?: ""
            val maxParticipants =
                backStackEntry.arguments?.getString("maxParticipants")?.toIntOrNull() ?: 0
            val friends = backStackEntry.arguments?.getString("friends")?.split(",") ?: emptyList()

            EventSummaryScreen(
                sport = sport,
                date = date,
                location = location,
                maxParticipants = maxParticipants,
                selectedCourt = location, // 🔹 Se usa el ID del centro deportivo
                friends = friends,
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.CENTERS) {
            CentersScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        //  Nueva Pantalla para Eventos Activos
        composable(Routes.ACTIVE_EVENTS) {
            ActiveEventsScreen(navController = navController)
        }

        //  Pantalla de Perfil
        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigate = { route -> navController.navigate(route) },
                onEditProfileClick = { navController.navigate(Routes.EDIT_PROFILE) },
                onHelpClick = { navController.navigate(Routes.HELP) },  // ✅ Navega a la pantalla de ayuda
                onTermsClick = { navController.navigate(Routes.TERMS) }, // ✅ Navega a Términos y Condiciones
                onNotificationsClick = { navController.navigate(Routes.NOTIFICATIONS) }, // ✅ Navega a Notificaciones
            )
        }

        // Pantalla de Edición de Perfil
        composable(Routes.EDIT_PROFILE) {
            val userViewModel: UserViewModel = hiltViewModel()
            val user by userViewModel.user.collectAsState()

            if (user != null) {
                EditProfileScreen(
                    userViewModel = userViewModel,
                    userData = user!!,
                    onBack = { navController.popBackStack() }
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

