package com.example.quicksports.presentation.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quicksports.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.quicksports.Screen
import com.example.quicksports.presentation.ViewModel.CenterViewModel
import com.example.quicksports.presentation.ViewModel.CrearEventoViewModel
import com.example.quicksports.presentation.ViewModel.SportsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CrearEventoScreen(
    navController: NavController,
    crearEventoViewModel: CrearEventoViewModel,
    centerViewModel: CenterViewModel = viewModel(),
    sportsViewModel: SportsViewModel = viewModel()
) {
    val selectedSport by crearEventoViewModel.selectedSport.collectAsState()
    val selectedCenter by crearEventoViewModel.selectedCenter.collectAsState()
    val sports by sportsViewModel.sports.collectAsState()
    val centers by centerViewModel.centros.collectAsState()

    val filteredCenters = selectedSport?.let { sport ->
        centers.filter { it.sportPrices.containsKey(sport.id) }
    } ?: centers

    val listState = rememberLazyListState()
    val showRightArrow by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) false
            else visibleItems.last().index < layoutInfo.totalItemsCount - 1
        }
    }
    val showLeftArrow by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) false
            else visibleItems.first().index > 0
        }
    }

    val fadeRight by animateFloatAsState(
        targetValue = if (showRightArrow) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "ArrowFadeRight"
    )
    val fadeLeft by animateFloatAsState(
        targetValue = if (showLeftArrow) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "ArrowFadeLeft"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF4B5320), Color.Black)
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selecciona un deporte",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                LazyRow(
                    state = listState,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(sports) { sport ->
                        Box(
                            modifier = Modifier
                                .width(180.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { crearEventoViewModel.selectSport(sport) },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = sport.imageRes),
                                contentDescription = sport.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(Color.Black.copy(alpha = 0.3f))
                            )
                            Text(
                                sport.name,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    shadow = Shadow(Color.Black, Offset(2f, 2f), 4f),
                                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                                )
                            )
                        }
                    }
                }

                if (fadeLeft > 0f) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Scroll izquierdo",
                        tint = Color.White.copy(alpha = fadeLeft * 0.4f),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 8.dp)
                            .size(32.dp)
                    )
                }
                if (fadeRight > 0f) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Scroll derecho",
                        tint = Color.White.copy(alpha = fadeRight * 0.4f),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                            .size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (selectedSport != null) "Centros disponibles para ${'$'}{selectedSport!!.name}" else "Todos los centros deportivos",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredCenters) { center ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (crearEventoViewModel.isCentroSeleccionado(center)) Color(0xFF4B5320) else Color(0xFF1C1C1C))
                            .clickable { crearEventoViewModel.selectCenter(center) }
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                text = center.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.poppins_regular))
                            )
                            Text(
                                text = center.address,
                                color = Color.LightGray,
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily(Font(R.font.poppins_regular))
                            )
                            Text(
                                text = "Tel: ${'$'}{center.contactPhone}",
                                color = Color.LightGray,
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily(Font(R.font.poppins_regular))
                            )
                            selectedSport?.id?.let { sportId ->
                                center.sportPrices[sportId]?.let { price ->
                                    Text(
                                        text = "Precio: €${'$'}price",
                                        color = Color(0xFF64FFDA),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.CrearEventoPaso2.route)
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(48.dp),
                enabled = selectedSport != null && selectedCenter != null,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCCCCCC)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Siguiente", color = Color.Black, fontFamily = FontFamily(Font(R.font.poppins_regular)))
            }
        }
    }
}
