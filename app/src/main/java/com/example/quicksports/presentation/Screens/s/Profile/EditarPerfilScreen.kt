package com.example.quicksports.presentation.Screens.s.Profile
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.presentation.ViewModel.UserViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfilScreen(
    userViewModel: UserViewModel = viewModel(),
    navController: NavController
) {
    LaunchedEffect(Unit) {
        userViewModel.loadCurrentUser()
    }

    val user = userViewModel.user.collectAsState().value

    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var domicilio by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            lastName = it.lastName
            telefono = it.telefono
            domicilio = it.domicilio
            fechaNacimiento = it.fechaNacimiento
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },

        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF0D47A1), Color.Black)
                    )
                )
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Editar perfil",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (user == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                OutlinedTextField(
                    value = user.email,
                    onValueChange = {},
                    label = { Text("Correo electrónico", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        disabledTextColor = Color.White,
                        focusedContainerColor = Color.White.copy(alpha = 0.05f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.03f),
                        disabledContainerColor = Color.White.copy(alpha = 0.03f),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                @Composable
                fun editableTextField(value: String, onValueChange: (String) -> Unit, label: String) {
                    OutlinedTextField(
                        value = value,
                        onValueChange = onValueChange,
                        label = { Text(label, color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Editar",
                                tint = Color.White.copy(alpha = 0.7f)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.White.copy(alpha = 0.05f),
                            unfocusedContainerColor = Color.White.copy(alpha = 0.03f),
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                            cursorColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                editableTextField(name, { name = it }, "Nombre")
                editableTextField(lastName, { lastName = it }, "Apellido")
                editableTextField(telefono, { telefono = it }, "Teléfono")
                editableTextField(domicilio, { domicilio = it }, "Domicilio")
                editableTextField(fechaNacimiento, { fechaNacimiento = it }, "Fecha de nacimiento")

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val updated = user.copy(
                            name = name,
                            lastName = lastName,
                            telefono = telefono,
                            domicilio = domicilio,
                            fechaNacimiento = fechaNacimiento
                        )
                        userViewModel.updateUserProfile(updated) { success ->
                            if (success) {
                                navController.popBackStack()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCCCCCC)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Guardar cambios", color = Color.Black)
                }
            }
        }
    }
}
