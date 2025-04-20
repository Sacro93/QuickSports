package com.example.quicksports.presentation.Screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.quicksports.R
import com.example.quicksports.presentation.Navigation.Screen
@Composable
fun BottomNavigationBar(
    navController: NavController,
    containerColor: Color = Color(0xFF121212).copy(alpha = 0.8f)
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = containerColor,
        tonalElevation = 4.dp
    ) {
        val items = listOf(
            Triple("Inicio", Icons.Default.Home, Screen.Home.route),
            Triple("Amistades", Icons.Default.Person, Screen.Amistades.route),
            Triple("Centros", Icons.Default.Place, Screen.Centros.route),
            Triple("Perfil", Icons.Default.AccountCircle, Screen.Perfil.route)
        )

        items.forEach { (label, icon, route) ->
            val selected = currentDestination == route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentDestination != route) {
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = if (selected) Color.White else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        color = if (selected) Color.White else Color.Gray,
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFF1E88E5).copy(alpha = 0.2f)
                ),
                alwaysShowLabel = true
            )
        }
    }
}
