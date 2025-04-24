package com.example.quicksports.presentation.View.Screens.Login_Register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quicksports.presentation.Navigation.Screen
import com.example.quicksports.presentation.View.components.QuickSportsTitle

@Composable
fun StartScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D47A1), Color(0xFF121212), Color(0xFF000000)
                    )
                )
            )
            .padding(horizontal = 32.dp, vertical = 48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuickSportsTitle()

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { navController.navigate(Screen.Login.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCCCCCC)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Iniciar Sesión",
                color = Color.Black,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { navController.navigate(Screen.Register.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.7f)),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
        ) {
            Text(
                text = "Registrarse",
            )
        }
    }
}
