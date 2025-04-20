package com.example.quicksports.presentation.Screens.s.Login_Register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.presentation.ViewModel.Login.LoginViewModel
import com.example.quicksports.R
import com.example.quicksports.presentation.Navigation.Screen
import com.example.quicksports.data.repository.AuthRepository
import com.example.quicksports.presentation.ViewModel.Login.LoginViewModelFactory
import com.example.quicksports.presentation.components.QuickSportsTitle
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel

) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val passwordVisible = remember { mutableStateOf(false) }

    val loginViewModel = viewModel

    val uiState by loginViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D47A1), Color(0xFF121212), Color(0xFF000000))
                )
            )
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        QuickSportsTitle()

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = loginViewModel::onEmailChange,
            label = { Text("Correo electrónico") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            colors = defaultFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = loginViewModel::onPasswordChange,
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(icon, contentDescription = null, tint = Color.White)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = defaultFieldColors()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = uiState.keepLoggedIn,
                onCheckedChange = loginViewModel::onKeepLoggedInChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.White,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.Black
                )
            )
            Text("Mantener sesión iniciada", color = Color.White)
        }

        if (uiState.errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                uiState.errorMessage!!,
                color = MaterialTheme.colorScheme.error,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                focusManager.clearFocus()
                loginViewModel.onLoginClick(
                    context = context,
                    onSuccess = {
                        Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Start.route) { inclusive = true }
                        }
                    },
                    onError = {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCCCCCC)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Ingresar", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.gmail),
                contentDescription = "Gmail",
                modifier = Modifier.size(28.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("Iniciar con Google", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = {
            navController.navigate("register")
        }) {
            Text("¿No tienes cuenta? Registrarse", color = Color.White)
        }
    }
}

