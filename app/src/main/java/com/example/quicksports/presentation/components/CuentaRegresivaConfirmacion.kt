package com.example.quicksports.presentation.components
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.core.content.edit
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CuentaRegresivaConCancelacion(
    centerPhone: String,
    onTimeout: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("evento_prefs", Context.MODE_PRIVATE) }

    val totalDuration = Duration.ofMinutes(5)
    val formatter = DateTimeFormatter.ofPattern("mm:ss")

    val startTimeMillis = remember {
        prefs.getLong("evento_start_time", -1L).takeIf { it > 0 }
            ?: run {
                val now = System.currentTimeMillis()
                prefs.edit() { putLong("evento_start_time", now) }
                now
            }
    }

    val remainingTime = remember { mutableStateOf(Duration.ZERO) }
    var timeoutHandled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            val now = System.currentTimeMillis()
            val elapsed = Duration.ofMillis(now - startTimeMillis)
            val remaining = totalDuration.minus(elapsed)
            remainingTime.value = if (!remaining.isNegative) remaining else Duration.ZERO

            if (remainingTime.value.isZero && !timeoutHandled) {
                timeoutHandled = true
                prefs.edit() { remove("evento_start_time") }
                onTimeout()
                break
            }
            delay(1000)
        }
    }

    if (remainingTime.value > Duration.ZERO) {
        Text(
            text = " Tiempo restante: ${formatter.format(LocalTime.MIDNIGHT.plus(remainingTime.value))}",
            color = Color.White.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        Text(
            text = " Tiempo agotado. El evento fue anulado automáticamente.",
            color = Color.Red.copy(alpha = 0.8f),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
