package com.example.quicksports.presentation.Screens.s.Profile
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quicksports.presentation.ViewModel.UserViewModel

@Composable
fun PerfilScreen(
    userViewModel: UserViewModel = viewModel(),
    onEditProfileClick: () -> Unit,
    onLogout: () -> Unit
) {
    val user by userViewModel.user.collectAsState()

    LaunchedEffect(user) {
        if (user == null) {
            userViewModel.loadCurrentUser()
        }
    }

    if (user == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val userData = user!!
        val userInitials = "${userData.name.firstOrNull() ?: ""}${userData.lastName.firstOrNull() ?: ""}".uppercase()

        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF1565C0), Color(0xFF000000))
                        )
                    )
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(100.dp))

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userInitials,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "${userData.name} ${userData.lastName}",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF121212))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            ProfileOption(
                                icon = Icons.Default.Person,
                                label = "Editar Perfil",
                                onClick = onEditProfileClick
                            )
                            ProfileOption(
                                icon = Icons.AutoMirrored.Filled.ExitToApp,
                                label = "Cerrar Sesión",
                                onClick = {
                                    userViewModel.logout()
                                    onLogout()
                                },
                                isWarning = true
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ProfileOption(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    isWarning: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isWarning) Color.Red else Color.White,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isWarning) Color.Red else Color.White
        )
    }
}