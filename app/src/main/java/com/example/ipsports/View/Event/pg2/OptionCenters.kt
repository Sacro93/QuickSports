package com.example.ipsports.View.Event.pg2

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ipsports.data.model.Center


@Composable
fun OptionCenters(
    selectedCenter: Center?,
    centers: List<Center>,
    onCenterSelected: (Center) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedCenter?.name ?: "Seleccionar Centro Deportivo")
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Abrir menú")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (centers.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No hay centros disponibles") },
                    onClick = { expanded = false }
                )
            } else {
                centers.forEach { center ->
                    DropdownMenuItem(
                        text = { Text(center.name) },
                        onClick = {
                            onCenterSelected(center)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

