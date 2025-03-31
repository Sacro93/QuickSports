package com.example.quicksports.presentation.Screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import com.example.quicksports.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle

@Composable
fun EventCard(
    imageResId: Int,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
            )
        }
    }
}
/*
@Composable
fun EventCard(imageResId: Int, title: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.98f else 1f,
        label = "cardScale"
    )

    Card(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .height(165.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = if (pressed) 10f else 18f
                shape = RoundedCornerShape(20.dp)
                clip = true
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.5f)
                            )
                        )
                    )
            )

            Text(
                text = title,
                style = TextStyle(
                    color = Color.White.copy(alpha = 0.75f), // más transparente
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic, // inclinación
                    letterSpacing = 1.sp // más "elástico"
                ),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}
*/