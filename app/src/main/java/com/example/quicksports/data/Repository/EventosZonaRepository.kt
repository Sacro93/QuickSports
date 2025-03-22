package com.example.quicksports.data.Repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.quicksports.data.models.EventoZona
import java.time.LocalDateTime

class EventosZonaRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getEventosZona(): List<EventoZona> = listOf(
        EventoZona("Fútbol", "Centro Deportivo Norte", "Calle 123, Barcelona", "Martín Rodríguez", LocalDateTime.now().plusDays(1)),
        EventoZona("Pádel", "Club Pádel Pro", "Avenida Diagonal 456, Barcelona", "Laura Vázquez", LocalDateTime.now().plusDays(2)),
        EventoZona("Tenis", "Tenis Club Barcelona", "Passeig de Gracia 78, Barcelona", "Diego Torres", LocalDateTime.now().plusDays(3)),
        EventoZona("Básquet", "Polideportivo Sant Gervasi", "Calle Marina 22, Barcelona", "Camila Pérez", LocalDateTime.now().plusDays(1).plusHours(3)),
        EventoZona("Fútbol", "Estadio Mini Barça", "Ronda Universitat 12, Barcelona", "Pedro Gómez", LocalDateTime.now().plusDays(2).plusHours(2)),
        EventoZona("Pádel", "Padel Club Raval", "Carrer del Carme 34, Barcelona", "Sofía Ruiz", LocalDateTime.now().plusDays(3).plusHours(1)),
        EventoZona("Tenis", "Academia Nadal BCN", "Carrer de Mallorca 101, Barcelona", "Javier Ortega", LocalDateTime.now().plusDays(4)),
        EventoZona("Básquet", "Basket Center Sants", "Carrer de Sants 77, Barcelona", "Martina López", LocalDateTime.now().plusDays(1)),
        EventoZona("Fútbol", "Cancha El Clot", "Carrer d'Aragó 45, Barcelona", "Alan Suárez", LocalDateTime.now().plusDays(5)),
        EventoZona("Tenis", "Top Tennis Club", "Gran Via 210, Barcelona", "Elena Martínez", LocalDateTime.now().plusDays(6))
    )
}