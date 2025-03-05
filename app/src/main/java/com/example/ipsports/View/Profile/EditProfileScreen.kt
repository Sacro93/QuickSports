package com.example.ipsports.View.Profile

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import com.example.ipsports.View.Reusable.ButtonPrimary
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.ipsports.R
import com.example.ipsports.View.Reusable.ReusableInputField
import com.example.ipsports.ViewModel.ui.UserViewModel
import com.example.ipsports.data.model.User
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EditProfileScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    userData: User,
    onBack: () -> Unit
) {
    var showPhotoDialog by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf(userData.name) }
    var apellido by remember { mutableStateOf(userData.surname) }
    var email by remember { mutableStateOf(userData.email) }
    var domicilio by remember { mutableStateOf(userData.location) }

    val avatarList = listOf(R.drawable.hombre, R.drawable.mujer)
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var selectedAvatar by remember {
        mutableIntStateOf(userData.profileImageUrl?.toIntOrNull() ?: avatarList.first())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1E88E5), Color(0xFF1565C0), Color(0xFF000000))
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onBack() }
            )
        }

        // 🔹 **Imagen de perfil**
        Box(
            modifier = Modifier.size(110.dp).clip(CircleShape).background(Color.Gray)
                .clickable { showPhotoDialog = true },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = selectedAvatar),
                contentDescription = "Avatar de usuario",
                modifier = Modifier.size(110.dp).clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Cambiar Foto",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { showPhotoDialog = true }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Cambiar Foto",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { showPhotoDialog = true }
        )

        Spacer(modifier = Modifier.height(20.dp))

        //  **Formulario**
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ReusableInputField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = "Nombre",
                    leadingIcon = Icons.Default.Person
                )
                ReusableInputField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    label = "Apellido",
                    leadingIcon = Icons.Default.Person
                )
                ReusableInputField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    leadingIcon = Icons.Default.Email
                )
                ReusableInputField(
                    value = domicilio,
                    onValueChange = { domicilio = it },
                    label = "Domicilio",
                    leadingIcon = Icons.Default.LocationOn
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 **Botón Guardar**
        ButtonPrimary(
            text = "Guardar Cambios",
            onClick = {
                val updatedUser = userData.copy(
                    name = nombre,
                    surname = apellido,
                    email = email,
                    location = domicilio,
                    profileImageUrl = selectedAvatar.toString() // 🔹 Guarda el ID del avatar en Firestore
                )

                val userId = FirebaseAuth.getInstance().currentUser?.uid
                    ?: return@ButtonPrimary // ✅ Obtener ID actual del usuario
                userViewModel.updateUser(userId, updatedUser) // ✅ Pasamos el `userId`
            },
            modifier = Modifier.align(Alignment.CenterHorizontally).width(250.dp)
        )

        // **Diálogo para elegir avatar**
        if (showPhotoDialog) {
            AvatarSelectionDialog(
                avatarList = avatarList,
                selectedAvatar = selectedAvatar,
                onAvatarSelected = { selectedAvatar = it },
                onDismiss = { showPhotoDialog = false }
            )
        }
    }
}
@Composable
fun AvatarSelectionDialog(
    avatarList: List<Int>,
    selectedAvatar: Int,
    onAvatarSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar Avatar") },
        text = {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(avatarList) { avatar ->
                    Image(
                        painter = painterResource(id = avatar),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(if (avatar == selectedAvatar) Color.Gray else Color.Transparent)
                            .clickable {
                                onAvatarSelected(avatar)
                                onDismiss()
                            }
                    )
                }
            }
        },
        confirmButton = {
            ButtonPrimary(text = "Cerrar", onClick = onDismiss, modifier = Modifier.fillMaxWidth())
        }
    )
}


