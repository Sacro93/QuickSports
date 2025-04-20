package com.example.quicksports.presentation.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.graphics.drawable.toDrawable

@Composable
fun TransparentSystemBars() {
    val view = LocalView.current
    val window = (view.context as Activity).window

    SideEffect {
        // Permite que el contenido se dibuje detrás de las barras del sistema
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // En lugar de usar la propiedad 'statusBarColor' directamente
        window.setBackgroundDrawable(android.graphics.Color.TRANSPARENT.toDrawable())

        val controller = WindowCompat.getInsetsController(window, view)

        // Apariencia oscura en las barras
        controller.isAppearanceLightStatusBars = false
        controller.isAppearanceLightNavigationBars = false

        // Ocultar completamente las barras
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())
    }
}
