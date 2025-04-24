package com.example.quicksports.presentation.View.Screens.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.quicksports.data.repository.AuthRepository
import com.example.quicksports.presentation.ViewModel.Sports.SportsViewModel
import com.example.quicksports.presentation.ViewModel.User.UserViewModel
import com.example.quicksports.presentation.ViewModel.User.UserViewModelFactory
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(AuthRepository())
    ),
    navController: NavController
) {
    LaunchedEffect(Unit) {
        userViewModel.loadCurrentUser()
    }

    val user = userViewModel.user.collectAsState().value
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var dateBirth by remember { mutableStateOf("") }
    val sportsViewModel: SportsViewModel = viewModel()
    val sports by sportsViewModel.sports.collectAsState()
    val selectedSport = remember { mutableStateListOf<Int>() }

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            lastName = it.lastName
            phone = it.phone
            address = it.address
            dateBirth = it.dateBirth
            selectedSport.clear()
            selectedSport.addAll(it.favoriteSports)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Editar perfil",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
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
                        colors = listOf(Color(0xFF0D47A1), Color(0xFF000000))
                    )
                )
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (user == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
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

                Spacer(modifier = Modifier.height(7.dp))

                editableField(name, { name = it }, "Nombre")
                editableField(lastName, { lastName = it }, "Apellido")
                editableField(phone, { phone = it }, "Teléfono")
                editableField(address, { address = it }, "Domicilio")
                editableField(dateBirth, { dateBirth = it }, "Fecha de nacimiento")

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "Deportes favoritos:",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        sports.forEach { sport ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Checkbox(
                                    checked = selectedSport.contains(sport.id),
                                    onCheckedChange = {
                                        if (it) selectedSport.add(sport.id)
                                        else selectedSport.remove(sport.id)
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color.White,
                                        uncheckedColor = Color.White,
                                        checkmarkColor = Color.Black
                                    )
                                )
                                Text(
                                    text = sport.name,
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val updated = user.copy(
                            name = name,
                            lastName = lastName,
                            phone = phone,
                            address = address,
                            dateBirth = dateBirth,
                            favoriteSports = selectedSport.toList()
                        )
                        userViewModel.updateUserProfile(updated) { success ->
                            if (success) {
                                navController.popBackStack()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCCCCCC)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Guardar cambios", color = Color.Black)
                }
            }
        }
    }
}


@Composable
fun editableField(value: String, onChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label, color = Color.White) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
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
    Spacer(modifier = Modifier.height(16.dp))
}