package com.example.quicksports

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CrearEvento : Screen("crear_evento")
    object CrearEventoPaso2 : Screen("crear_evento_paso2")
    object TusEventos : Screen("tus_eventos")
    object EventosZona : Screen("eventos_zona")
    object Amistades : Screen("amistades")
    object Centros : Screen("centros")
    object Perfil : Screen("perfil")
    object FriendSelector : Screen("friend_selector")
}
