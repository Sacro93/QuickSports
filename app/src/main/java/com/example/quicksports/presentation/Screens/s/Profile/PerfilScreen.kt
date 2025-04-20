package com.example.quicksports.presentation.Screens.s.Profile
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quicksports.presentation.ViewModel.User.UserViewModel
import com.example.quicksports.R
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.quicksports.presentation.Screens.BottomNavigationBar
import androidx.core.content.edit
import com.example.quicksports.data.repository.AuthRepository
import com.example.quicksports.presentation.ViewModel.User.UserViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PerfilScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(AuthRepository())
    ),
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
        val context = LocalContext.current

        var selectedAvatar by rememberSaveable {
            mutableIntStateOf(
                context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    .getInt("selected_avatar", R.drawable.hombre)
            )
        }

        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController)
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF0D47A1), Color(0xFF121212), Color(0xFF000000))
                        )
                    )
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(90.dp))

                // AVATAR SELECTOR USANDO COMPONENTE PERSONALIZADO
                AvatarSelector(
                    selectedAvatar = selectedAvatar,
                    onAvatarSelected = { selectedAvatar = it }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${userData.name} ${userData.lastName}",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = userData.email,
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PerfilInfoBox(title = "Plan Premium", subtitle = "Tu plan", modifier = Modifier.weight(1f))
                    PerfilInfoBox(
                        title = "Invitaciones",
                        subtitle = "3 activas",
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                Toast.makeText(context, "Funcionalidad próximamente", Toast.LENGTH_SHORT).show()
                            }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                PerfilCardSection(
                    items = listOf(
                        "Editar Perfil" to onEditProfileClick,
                        "Ayuda" to { Toast.makeText(context, "Funcionalidad próximamente", Toast.LENGTH_SHORT).show() },
                        "Método de pago" to { Toast.makeText(context, "Funcionalidad próximamente", Toast.LENGTH_SHORT).show() },
                        "Documentos y extractos" to {},
                        "Bandeja de entrada" to { Toast.makeText(context, "Sin mensajes aún", Toast.LENGTH_SHORT).show() }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                PerfilCardSection(
                    items = listOf(
                        "Configuración de notificaciones" to {
                            Toast.makeText(context, "Funcionalidad próximamente", Toast.LENGTH_SHORT).show()
                        }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                PerfilCardSection(
                    items = listOf(
                        "Cerrar Sesión" to {
                            userViewModel.logout()
                            onLogout()
                        }
                    )
                )
            }
        }
    }
}




@Composable
fun PerfilInfoBox(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(85.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
fun PerfilCardSection(items: List<Pair<String, () -> Unit>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            items.forEach { (label, action) ->
                Text(
                    text = label,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { action() }
                        .padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}



@Composable
fun AvatarSelector(
    selectedAvatar: Int,
    onAvatarSelected: (Int) -> Unit,
    avatarOptions: List<Int> = listOf(R.drawable.hombre, R.drawable.mujer_2)
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.3f))
            .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = selectedAvatar),
            contentDescription = "Avatar",
            modifier = Modifier.fillMaxSize().clip(CircleShape)
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Selecciona un avatar", color = Color.White) },
            containerColor = Color(0xFF1E1E1E),
            confirmButton = {},
            text = {
                Column {
                    avatarOptions.forEach { avatarRes ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                                        .edit() { putInt("selected_avatar", avatarRes) }
                                    onAvatarSelected(avatarRes)
                                    showDialog = false
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = avatarRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                if (avatarRes == R.drawable.hombre) "Avatar Hombre" else "Avatar Mujer",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        )
    }
}
