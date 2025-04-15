package com.example.quicksports.presentation.Screens.s.Login_Register
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.presentation.ViewModel.RegisterViewModel
import com.example.quicksports.presentation.ViewModel.SportsViewModel
import com.example.quicksports.presentation.components.QuickSportsTitle

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val passwordVisible = remember { mutableStateOf(false) }
    val sportsViewModel: SportsViewModel = viewModel()
    val deportes by sportsViewModel.sports.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D47A1), Color(0xFF121212), Color(0xFF000000))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp, bottom = 64.dp), // <- Ajustamos aquí
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Crear cuenta",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = uiState.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = defaultFieldColors()
            )

            OutlinedTextField(
                value = uiState.lastName,
                onValueChange = viewModel::onLastNameChange,
                label = { Text("Apellido") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = defaultFieldColors()
            )

            OutlinedTextField(
                value = uiState.telefono,
                onValueChange = viewModel::onTelefonoChanged,
                label = { Text("Teléfono") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                colors = defaultFieldColors()
            )

            OutlinedTextField(
                value = uiState.domicilio,
                onValueChange = viewModel::onDomicilioChanged,
                label = { Text("Domicilio") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = defaultFieldColors()
            )

            OutlinedTextField(
                value = uiState.fechaNacimiento,
                onValueChange = viewModel::onFechaNacimientoChanged,
                label = { Text("Fecha de nacimiento (dd/mm/aaaa)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = defaultFieldColors()
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Correo electrónico") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = defaultFieldColors()
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChanged,
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(icon, contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = defaultFieldColors()
            )

            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = viewModel::onRepeatPasswordChanged,
                label = { Text("Repetir contraseña") },
                singleLine = true,
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = defaultFieldColors()
            )

            Text(
                "Deportes favoritos",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                modifier = Modifier.align(Alignment.Start)
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                deportes.forEach { sport ->
                    val isSelected = uiState.deportesFavoritos.contains(sport.id)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onDeporteFavoritoToggle(sport.id) }
                    ) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = { viewModel.onDeporteFavoritoToggle(sport.id) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.White,
                                uncheckedColor = Color.White,
                                checkmarkColor = Color.Black
                            )
                        )
                        Text(sport.name, color = Color.White)
                    }
                }
            }

            if (uiState.errorMessage != null) {
                Text(uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.onRegisterClick(
                        onSuccess = {
                            Toast.makeText(context, "Revisa tu correo para confirmar el registro", Toast.LENGTH_LONG).show()
                            navController.popBackStack()
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
                Text("Registrarse", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


@Composable
fun defaultFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedContainerColor = Color.White.copy(alpha = 0.05f),
    unfocusedContainerColor = Color.White.copy(alpha = 0.03f),
    focusedLabelColor = Color.White,
    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
    cursorColor = Color.White
)


