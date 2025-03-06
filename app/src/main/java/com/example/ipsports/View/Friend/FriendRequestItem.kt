package com.example.ipsports.View.Friend
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import com.example.ipsports.data.model.FriendRequest
@Composable
fun FriendRequestItem(
    request: FriendRequest,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Solicitud de: ${request.senderName}", color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onAccept) {
                Icon(Icons.Default.Check, contentDescription = "Aceptar", tint = Color.Green)
            }
            IconButton(onClick = onReject) {
                Icon(Icons.Default.Close, contentDescription = "Rechazar", tint = Color.Red)
            }
        }
    }
}

