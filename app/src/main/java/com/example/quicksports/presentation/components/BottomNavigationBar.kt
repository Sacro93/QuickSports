package com.example.quicksports.presentation.Screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.navigation.NavController
import com.example.quicksports.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        BottomNavItem(
            label = "Inicio",
            icon = Icons.Default.Home,
            onClick = { navController.navigate(Screen.Home.route) }
        )
        BottomNavItem(
            label = "Amistades",
            icon = Icons.Default.Person,
            onClick = { navController.navigate(Screen.Amistades.route) }
        )
        BottomNavItem(
            label = "Centros",
            icon = Icons.Default.Place,
            onClick = { navController.navigate(Screen.Centros.route) }
        )
        BottomNavItem(
            label = "Perfil",
            icon = Icons.Default.AccountCircle,
            onClick = { navController.navigate(Screen.Perfil.route) }
        )
    }
}

@Composable
fun BottomNavItem(label: String, icon: ImageVector, onClick: () -> Unit) {
    NavigationRailItem(
        selected = false,
        onClick = onClick,
        icon = {
            Icon(imageVector = icon, contentDescription = label)
        },
        label = {
            Text(text = label)
        }
    )
}
