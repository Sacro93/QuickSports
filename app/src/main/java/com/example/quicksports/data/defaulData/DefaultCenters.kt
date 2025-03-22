package com.example.quicksports.data.defaulData

import com.example.quicksports.data.models.Center

object DefaultCenters {
    fun get(): List<Center> = listOf(
        Center(
            1,
            "Polideportivo Les Corts",
            "Carrer de Numància 60, Barcelona",
            "933456789",
            mapOf(1 to 25.0, 3 to 18.0)
        ),
        Center(
            2,
            "Club Esportiu Clot",
            "Carrer del Clot 123, Barcelona",
            "934112233",
            mapOf(1 to 20.0, 2 to 30.0)
        ),
        Center(
            3,
            "Barça Sports Center",
            "Av. Diagonal 456, Barcelona",
            "932223344",
            mapOf(4 to 22.0)
        ),
        Center(
            4,
            "Gimnàs Gràcia",
            "Carrer Gran de Gràcia 75, Barcelona",
            "938887766",
            mapOf(2 to 28.0)
        ),
        Center(
            5,
            "Club Tennis Sant Gervasi",
            "Passeig de Sant Gervasi 102, Barcelona",
            "931234567",
            mapOf(3 to 15.0)
        ),
        Center(
            6,
            "Padel Club Poblenou",
            "Carrer de Llull 345, Barcelona",
            "936543210",
            mapOf(4 to 26.0)
        ),
        Center(
            7,
            "Escola Esportiva Horta",
            "Carrer de Lisboa 8, Barcelona",
            "930998877",
            mapOf(1 to 22.0, 2 to 29.0)
        ),
        Center(
            8,
            "Centre Olímpic",
            "Rambla del Poblenou 78, Barcelona",
            "932112233",
            mapOf(1 to 27.0, 3 to 20.0)
        ),
        Center(
            9,
            "Zona Esportiva Eixample",
            "Carrer de València 201, Barcelona",
            "934443322",
            mapOf(2 to 25.0, 4 to 24.0)
        ),
        Center(
            10,
            "Futbol 7 Sants",
            "Carrer de Sants 180, Barcelona",
            "939998877",
            mapOf(1 to 23.0)
        ),
        Center(
            11,
            "Bàsquet Club Raval",
            "Carrer de l'Hospital 31, Barcelona",
            "931112233",
            mapOf(2 to 26.0)
        ),
        Center(
            12,
            "Poliesportiu Sarrià",
            "Carrer de Cornet i Mas 14, Barcelona",
            "935556677",
            mapOf(1 to 21.0, 4 to 25.0)
        ),
        Center(
            13,
            "Tenis Montjuïc",
            "Carrer de Montjuïc 58, Barcelona",
            "930123456",
            mapOf(3 to 19.0)
        ),
        Center(14, "Padel BCN", "Carrer de Balmes 91, Barcelona", "932456789", mapOf(4 to 27.0)),
        Center(
            15,
            "Esports Vila Olímpica",
            "Av. Icària 200, Barcelona",
            "938765432",
            mapOf(1 to 24.0, 2 to 31.0, 3 to 16.0)
        ),
        Center(
            16,
            "Pista Esportiva La Barceloneta",
            "Passeig Joan de Borbó 50, Barcelona",
            "930000111",
            mapOf(1 to 22.5)
        ),
        Center(
            17,
            "Cancha Bonanova",
            "Plaça Bonanova 12, Barcelona",
            "939393939",
            mapOf(2 to 32.0)
        ),
        Center(18, "Tenis Club Diagonal", "Diagonal 720, Barcelona", "934556677", mapOf(3 to 17.0)),
        Center(
            19,
            "Padel Garden",
            "Carrer de Mallorca 340, Barcelona",
            "937654321",
            mapOf(4 to 23.5)
        ),
        Center(
            20,
            "Multiesport BCN",
            "Carrer Marina 200, Barcelona",
            "931212121",
            mapOf(1 to 26.0, 2 to 30.0, 4 to 25.0)
        )
    )
}