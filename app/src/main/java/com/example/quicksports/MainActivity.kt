package com.example.quicksports

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.quicksports.presentation.Navigation.Navigation
import com.example.quicksports.presentation.components.TransparentSystemBars
import com.example.quicksports.ui.theme.QuickSportsTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickSportsTheme {
                TransparentSystemBars()
                Navigation()
            }
        }
    }
}