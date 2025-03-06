package com.example.ipsports.data.model

//Relación entre centros deportivos y los deportes que ofrecen
data class CenterWithSports(
    val center: Center = Center(),
    val sports: List<Sport> = emptyList()
)