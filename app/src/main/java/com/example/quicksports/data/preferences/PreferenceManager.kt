package com.example.quicksports.data.preferences

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.example.quicksports.R

object PreferenceManager {
    private const val PREF_NAME = "quick_sports_prefs"
    private const val KEY_KEEP_LOGGED_IN = "keep_logged_in"

    fun setKeepLoggedIn(context: Context, value: Boolean) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit() {
                putBoolean(KEY_KEEP_LOGGED_IN, value)
            }
    }

    fun shouldKeepLoggedIn(context: Context): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_KEEP_LOGGED_IN, false)
    }
}


@Composable
fun SafeAvatarImage(resourceId: Int?, fallback: Int = R.drawable.mujer_2) {
    val context = LocalContext.current
    val finalResId = remember(resourceId) {
        val resId = resourceId ?: fallback
        val fallbackRes = fallback

        // Verificamos si el recurso existe en tiempo de ejecución
        val typedValue = context.resources.getIdentifier(
            context.resources.getResourceEntryName(resId),
            "drawable",
            context.packageName
        )

        if (typedValue != 0) resId else fallbackRes
    }

    Image(
        painter = painterResource(id = finalResId),
        contentDescription = null,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
    )
}

