package com.example.quicksports.presentation.components
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun EstadoConfirmacionEvento(
    creationTime: LocalDateTime,
    centerPhone: String
) {
    val remainingDuration = remember { mutableStateOf(Duration.ZERO) }
    val formatter = DateTimeFormatter.ofPattern("mm:ss")

    LaunchedEffect(creationTime) {
        while (true) {
            val elapsed = Duration.between(creationTime, LocalDateTime.now())
            val remaining = Duration.ofMinutes(10).minus(elapsed)
            remainingDuration.value = if (!remaining.isNegative) remaining else Duration.ZERO
            delay(1000)
        }
    }

    val isPending = remainingDuration.value > Duration.ZERO
    val timeText = formatter.format(LocalTime.MIDNIGHT.plus(remainingDuration.value))

    val backgroundColor = if (isPending) Color(0xFF2E7D32).copy(alpha = 0.15f) else Color(0xFFB71C1C).copy(alpha = 0.15f)
    val icon = if (isPending) Icons.Default.Schedule else Icons.Default.Warning
    val iconTint = if (isPending) Color(0xFF66BB6A) else Color(0xFFEF5350)
    val message = if (isPending)
        "Pendiente de confirmación. Tiempo restante: $timeText"
    else
        " Tiempo de confirmación pasado. Contactate al $centerPhone"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall.copy(
                )
            )
        }
    }
}
