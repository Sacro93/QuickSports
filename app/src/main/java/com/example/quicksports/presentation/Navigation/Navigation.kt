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
import com.example.quicksports.presentation.Screens.CreateEventScreen
import com.example.quicksports.presentation.Screens.HomeScreen
import com.example.quicksports.presentation.View.Screens.Centros.CentersScreen
import com.example.quicksports.presentation.View.Screens.Evento.CreateEventScreen2
import com.example.quicksports.presentation.View.Screens.Evento.EventsZonaScreen
import com.example.quicksports.presentation.View.Screens.Evento.YourEventsScreen
import com.example.quicksports.presentation.View.Screens.Faqs.FaqScreen
import com.example.quicksports.presentation.View.Screens.Friends.FriendSelectorScreen
import com.example.quicksports.presentation.View.Screens.Friends.FriendsScreen
import com.example.quicksports.presentation.View.Screens.Login_Register.LoginScreen
import com.example.quicksports.presentation.View.Screens.Login_Register.RegisterScreen
import com.example.quicksports.presentation.View.Screens.Login_Register.StartScreen
import com.example.quicksports.presentation.View.Screens.Notificaciones.NotificationScreen
import com.example.quicksports.presentation.View.Screens.Profile.EditProfileScreen
import com.example.quicksports.presentation.View.Screens.Profile.ProfileScreen

import com.example.quicksports.presentation.ViewModel.Center.CenterViewModelFactory
import com.example.quicksports.presentation.ViewModel.Eventos.CreateEventViewModel
import com.example.quicksports.presentation.ViewModel.Eventos.CreateEventViewModelFactory
import com.example.quicksports.presentation.ViewModel.Eventos.EventAreaViewModelFactory
import com.example.quicksports.presentation.ViewModel.Eventos.YourEventsViewModelFactory
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
            RegisterScreen(navController, registerViewModel)
        }

        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(AuthRepository())
            )
            LoginScreen(navController, loginViewModel)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.CreateEvent.route) {
            val createEventViewModel: CreateEventViewModel = viewModel(
                factory = CreateEventViewModelFactory()
            )
            CreateEventScreen(navController, createEventViewModel)
        }

        composable(Screen.CreateEvent2.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screen.CreateEvent.route)
            }
            val createEventViewModel: CreateEventViewModel = viewModel(
                parentEntry,
                factory = CreateEventViewModelFactory()
            )
            CreateEventScreen2(navController, createEventViewModel)
        }

        // Selector de amigos, también comparte el ViewModel
        composable(Screen.FriendSelector.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Screen.CreateEvent.route)
            }
            val createEventViewModel: CreateEventViewModel = viewModel(
                parentEntry,
                factory = CreateEventViewModelFactory()
            )
            FriendSelectorScreen(navController, createEventViewModel)
        }

        composable(Screen.YourEvents.route) {
            YourEventsScreen(
                navController = navController,
                viewModel = viewModel(factory = YourEventsViewModelFactory(LocalContext.current.applicationContext as Application))
            )
        }

        composable(Screen.EventZone.route) {
            EventsZonaScreen(
                navController = navController,
                viewModel = viewModel(factory = EventAreaViewModelFactory())
            )
        }

        composable(Screen.Friends.route) {
            FriendsScreen(
                navController,
                friendsViewModel = viewModel(factory = FriendsViewModelFactory(LocalContext.current.applicationContext as Application))
            )
        }

        composable(Screen.Centers.route) {
            CentersScreen(
                navController,
                centerViewModel = viewModel(factory = CenterViewModelFactory(LocalContext.current.applicationContext as Application)),
                sportsViewModel = viewModel(factory = SportsViewModelFactory(LocalContext.current.applicationContext as Application))
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                userViewModel = viewModel(factory = UserViewModelFactory(AuthRepository())),
                onEditProfileClick = { navController.navigate(Screen.EditProfile.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                userViewModel = viewModel(factory = UserViewModelFactory(AuthRepository())),
                navController = navController
            )
        }

        composable(Screen.FAQ.route) {
            FaqScreen(navController)
        }

        composable(Screen.NotificationsScreen.route) {
            NotificationScreen(navController)
        }
    }
}
