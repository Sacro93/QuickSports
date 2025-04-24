package com.example.quicksports.presentation.Screens.Principales.Login_Register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.shadow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quicksports.presentation.ViewModel.Login.LoginViewModel
import com.example.quicksports.presentation.Navigation.Screen
import com.example.quicksports.presentation.components.QuickSportsTitle


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val passwordVisible = remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0D47A1), Color(0xFF121212), Color(0xFF000000))
                )
            )
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(300.dp))

        QuickSportsTitle()

        Spacer(modifier = Modifier.height(150.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Correo electrónico", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.White) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            colors = defaultFieldColors()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Contraseña", color = Color.White) },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White) },
            trailingIcon = {
                val icon = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(icon, contentDescription = null, tint = Color.White)
                }
            },
            singleLine = true,
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = defaultFieldColors()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.keepLoggedIn,
                    onCheckedChange = viewModel::onKeepLoggedInChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White,
                        uncheckedColor = Color.White,
                        checkmarkColor = Color.Black
                    )
                )
                Text("Recordarme", color = Color.White)
            }

            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Color(0xFFCCCCCC),
                fontSize = 13.sp,
                modifier = Modifier
                    .clickable {
                        if (uiState.email.isNotBlank()) {
                            viewModel.onPasswordResetRequest(
                                context = context,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Te enviamos un correo para restablecer tu contraseña",
                                        Toast.LENGTH_LONG
                                    ).show()
                                },
                                onError = {
                                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                                }
                            )
                        } else {
                            Toast.makeText(context, "Ingresá tu correo primero", Toast.LENGTH_SHORT).show()
                        }
                    }
            )

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
                viewModel.onLoginClick(
                    context = context,
                    onSuccess = {
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
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCCCCCC)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Iniciar sesión", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("¿No tienes cuenta?", color = Color.White.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "Regístrate",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
