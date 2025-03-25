package com.example.quicksports.presentation.Screens.s.Profile
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.presentation.ViewModel.UserViewModel
@Composable
fun EditarPerfilScreen(
    userViewModel: UserViewModel = viewModel(),
    navController: NavController
) {
    // 👇 Cargamos el usuario apenas entra
    LaunchedEffect(Unit) {
        userViewModel.loadCurrentUser()
    }

    val user = userViewModel.user.collectAsState().value

    // Estados locales
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var domicilio by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }

    // Cuando user cambia y no es null, inicializamos los campos
    LaunchedEffect(user) {
        user?.let {
            name = it.name
            lastName = it.lastName
            telefono = it.telefono
            domicilio = it.domicilio
            fechaNacimiento = it.fechaNacimiento
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Editar perfil", style = MaterialTheme.typography.headlineMedium)

        if (user == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
            OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellido") })
            OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") })
            OutlinedTextField(value = domicilio, onValueChange = { domicilio = it }, label = { Text("Domicilio") })
            OutlinedTextField(value = fechaNacimiento, onValueChange = { fechaNacimiento = it }, label = { Text("Fecha de nacimiento") })

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
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }

            TextButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}
