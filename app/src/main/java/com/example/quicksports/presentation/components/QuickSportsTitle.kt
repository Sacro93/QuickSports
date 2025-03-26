package com.example.quicksports.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quicksports.R

@Composable
fun QuickSportsTitle() {
    val fontFamily = FontFamily(
        Font(R.font.blackopsone_regular, FontWeight.Normal)
    )

    Text(
        text = "Quick Sports",
        fontFamily = fontFamily,
        fontSize = 38.sp,
        color = Color.White,
        modifier = Modifier.padding(top = 24.dp),
        letterSpacing = 1.sp
    )
}
